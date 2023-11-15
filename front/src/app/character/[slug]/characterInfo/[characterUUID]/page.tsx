'use client';

import React, { useEffect, useRef, useState } from 'react';
import { FaRegTrashAlt, FaMinus } from 'react-icons/fa';
import { HiPlus } from 'react-icons/hi';
import Image from 'next/image';
import { getS3URL, uploadImage } from '@/service/image/image';
import {
  characterType,
  informationType,
  relationshipType,
} from '@/model/charactor';
import UploadState from '@/components/state/UploadState';
import styles from '@/service/cssStyle/scrollbar.module.css';
import {
  deleteCharacter,
  getCharacter,
  getCharacterByName,
  patchCharacter,
} from '@/service/api/character';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { GiCancel } from 'react-icons/gi';
import CharacterUpperGroup from '@/components/character/CharacterUpperGroup';

type Props = {
  params: {
    characterUUID: string;
    slug: string;
  };
};

const listener = (e: any) => {
  e.preventDefault();
  e.returnValue = '';
};

const enablePrevent = () => {
  window.addEventListener('beforeunload', listener);
};

const disablePrevent = () => {
  window.removeEventListener('beforeunload', listener);
};

export default function page({ params }: Props) {
  const router = useRouter();
  const queryClient = useQueryClient();

  const [isFetched, setIsFetched] = useState<boolean>(false);
  const [isNameChanged, setIsNameChanged] = useState<boolean>(false);

  const { data: characterData }: UseQueryResult<characterType> = useQuery({
    queryKey: ['character', params.characterUUID],
    queryFn: () =>
      getCharacter({ workspace: params.slug, uuid: params.characterUUID }),
    onSuccess: (data) => {
      setImageInput(data?.characterImage || '');
      setImageUrl(data?.characterImage || '');
      setNameInput(data?.characterName || '');
      setInformationInput(data?.information || []);
      setRelationInput(data?.relations || []);
      setdescriptionInput(data?.description || '');
      setGroupUUID(data?.groupUUID || null);
    },
    onError: () => {
      router.push(`/character/${params.slug}`);
    },
    refetchOnWindowFocus: true,
    refetchOnMount: true,
    staleTime: 0,
  });

  const [otherCharacterNameInput, setOtherCharacterNameInput] =
    useState<string>('');
  const [otherCharacterName, setOtherCharacterName] = useState<string>('');

  const { data: otherCharacterData }: UseQueryResult<characterType[]> =
    useQuery({
      queryKey: ['otherCharacter', otherCharacterName],
      queryFn: () => getCharacterByName(params.slug, otherCharacterName),
    });

  const putCharacterMutation = useMutation({
    mutationKey: ['otherCharacter', otherCharacterName],
    mutationFn: () =>
      patchCharacter({
        workspace: params.slug,
        params: params.characterUUID,
        body: {
          ...character,
          groupUUID: groupUUID,
        },
      }),
    onSuccess: () => {
      setLoadingState(3);
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
    onError: () => {
      setLoadingState(4);
    },
    onMutate: () => {
      setLoadingState(2);
    },
  });

  const deleteCharacterMutation = useMutation({
    mutationFn: () =>
      deleteCharacter({ workspace: params.slug, uuid: params.characterUUID }),
    onSuccess: () => {
      if (groupUUID) {
        router.push(`/character/${params.slug}/${groupUUID}`);
      } else {
        router.push(`/character/${params.slug}`);
      }
    },
    onError: () => {
      setLoadingState(4);
    },
  });

  const [character, setCharacter] = useState<characterType>({
    characterUUID: '',
    characterName: '',
    characterImage: '',
    description: '',
    information: [],
    relations: [],
  });

  const ref = useRef<characterType | null>(null);

  const [characterImageInput, setImageInput] = useState<string | null>('');
  const [characterImageUrl, setImageUrl] = useState<string | null>('');
  const imgRef = useRef<HTMLInputElement>(null);
  const [lastUploadTime, setLastUploadTime] = useState<number>(0);
  const uploadInterval = 3000;

  const [width, setWidth] = useState(100);
  const nameRef = useRef<HTMLInputElement>(null);
  const [nameInput, setNameInput] = useState<string | null>('');

  const [descriptionInput, setdescriptionInput] = useState<string | null>('');

  const [informationInput, setInformationInput] = useState<
    informationType[] | null
  >([]);

  const [relationshipInput, setRelationInput] = useState<
    relationshipType[] | null
  >([]);

  const [groupUUID, setGroupUUID] = useState<string | null>('');

  const [relationCharacterSearchInput, setRelationCharacterSearchInput] =
    useState<number>(-1);
  const [loadingState, setLoadingState] = useState<number>(0);

  const updateCharacter = () => {
    setCharacter((prev) => ({
      ...prev,
      characterName: nameInput,
      description: descriptionInput,
      characterImage: characterImageInput,
      relations: relationshipInput || [],
      information: informationInput,
    }));
  };
  const [filteredRelationCharacterData, setFilteredRelationCharacterData] =
    useState<characterType[]>([]);

  useEffect(() => {
    setFilteredRelationCharacterData(
      otherCharacterData?.filter(
        (obj) =>
          !relationshipInput
            ?.map((obj) => obj.targetUUID)
            .includes(obj.characterUUID) &&
          obj.characterUUID !== params.characterUUID,
      ) || [],
    );
  }, [otherCharacterData, otherCharacterName, relationCharacterSearchInput]);

  useEffect(() => {
    const debounce = setTimeout(
      () => {
        return setOtherCharacterName(otherCharacterNameInput);
      },
      isFetched ? 500 : 0,
    );
    return () => {
      clearTimeout(debounce);
    };
  }, [otherCharacterNameInput]);

  useEffect(() => {
    const debounce = setTimeout(
      () => {
        return updateCharacter();
      },
      isFetched ? 1000 : 0,
    );
    return () => {
      clearTimeout(debounce);
    };
  }, [
    nameInput,
    descriptionInput,
    characterImageInput,
    informationInput,
    relationshipInput,
  ]);

  useEffect(() => {
    if (
      ref.current &&
      JSON.stringify(ref.current) !== JSON.stringify(character)
    ) {
      if (isFetched) {
        putCharacterMutation.mutate();
        if (isNameChanged) {
          queryClient.invalidateQueries(['characterDirectory']);
          setIsNameChanged(false);
        }
      } else {
        setIsFetched(true);
      }
    } else {
      setLoadingState(0);
    }
    ref.current = JSON.parse(JSON.stringify(character));
  }, [character]);

  useEffect(() => {
    if (nameRef !== null && nameRef.current !== null) {
      if (nameRef.current.offsetWidth > 100) {
        setWidth(nameRef.current.offsetWidth + 5);
      } else {
        setWidth(100);
      }
      setIsNameChanged(true);
    }
  }, [nameInput]);

  const loaderProp = ({ src }: any) => {
    return src;
  };

  useEffect(() => {
    if (loadingState === 1 || loadingState === 2) {
      enablePrevent();
    } else {
      disablePrevent();
    }
  }, [loadingState]);

  return (
    <div
      className="select-none w-full h-screen overflow-y-scroll scrollbar-hide"
      onClick={() => {
        setRelationCharacterSearchInput(-1);
      }}
    >
      <div className="w-[60vw] min-w-[50rem] max-w-[100rem] ml-32 py-20 ">
        {/* 상단 타이틀 메뉴 + 로딩 상태 */}
        <CharacterUpperGroup parentUUID={groupUUID || ''} slug={params.slug} />
        <div className="flex items-end justify-between">
          <div className="flex items-center">
            <div>
              <span
                ref={nameRef}
                className="invisible opacity-0 absolute text-4xl font-extrabold"
              >
                {nameInput}
              </span>
              <input
                className="text-4xl font-extrabold max-w-[30rem] truncate"
                style={{ width }}
                type="text"
                onChange={(e) => {
                  setNameInput(e.target.value);
                  setLoadingState(1);
                }}
                value={nameInput || ''}
              />
            </div>

            <FaRegTrashAlt
              className="text-2xl mb-1 cursor-pointer"
              onClick={() => {
                deleteCharacterMutation.mutate();
              }}
            />
          </div>

          <UploadState state={loadingState} />
        </div>
        {/* 캐릭터 이미지 및 설명 */}
        <div className="flex h-64 mt-6">
          <div className="relative w-56 h-56 mr-10 place-self-end">
            {characterImageUrl !== '' &&
            characterImageUrl !== undefined &&
            characterImageUrl !== null ? (
              <>
                <Image
                  src={characterImageUrl}
                  alt="캐릭터 상세 이미지"
                  priority={true}
                  className="object-cover w-full h-full cursor-pointer"
                  height={1000}
                  width={1000}
                  onClick={() => {
                    imgRef?.current?.click();
                  }}
                  loader={loaderProp}
                  unoptimized={true}
                />
                <GiCancel
                  className="absolute top-0 right-0 w-10 h-10 cursor-pointer text-red-600 hover:text-red-800 font-extrabold z-10"
                  onClick={() => {
                    setImageUrl(null);
                    setImageInput(null);
                    setLoadingState(1);
                  }}
                ></GiCancel>
              </>
            ) : (
              <Image
                src="/images/default_character.png"
                alt="캐릭터 상세 이미지"
                priority={true}
                className="object-contain w-full h-full cursor-pointer"
                height={1000}
                width={1000}
                onClick={() => {
                  imgRef?.current?.click();
                }}
              />
            )}

            <input
              type="file"
              className="hidden"
              ref={imgRef}
              accept="characterImage/*"
              onChange={(e) => {
                const now = Date.now();

                if (now - lastUploadTime < uploadInterval) {
                  alert('이미지 업로드 간격이 너무 짧아요!');
                  return;
                } else {
                  //이미지 업로드
                  const targetFiles = (e.target as HTMLInputElement)
                    .files as FileList;
                  if (targetFiles[0]) {
                    const targetFile = targetFiles[0];
                    const yeah = async () => {
                      const url = await getS3URL(targetFile.name);
                      // 이후에 이미지를 S3 서버에 전송, 실패하면 백엔드에 실패 및 url 삭제 요청
                      const imgUrl = url.split('?')[0];
                      // 업로드 실패 시
                      if (!(await uploadImage(imgUrl, targetFile))) {
                      } else {
                        setImageUrl(window.URL.createObjectURL(targetFile));
                        setImageInput(imgUrl);
                        setLastUploadTime(now);
                      }
                    };
                    yeah();
                  }
                }

                setLoadingState(1);
              }}
            />
          </div>
          <div className="flex flex-col flex-grow justify-between">
            <p className="text-xl font-bold">캐릭터 설명</p>
            <div className="border-2 border-gray-300 rounded-xl h-56 p-2">
              <textarea
                className={`${styles.scroll} resize-none outline-none font-bold text-lg w-full h-full`}
                value={descriptionInput || ''}
                onChange={(e) => {
                  setLoadingState(1);
                  if (e.target.value.length < 1000) {
                    setdescriptionInput(e.target.value);
                  } else {
                    alert('글자 수 제한 1000자!');
                  }
                }}
              />
            </div>
          </div>
        </div>

        {/* 기본 정보 */}
        <div className="mt-8">
          <p className="text-xl font-extrabold">기본 정보</p>
          <table className="text-xl border w-full border-gray-300 rounded-xl border-separate border-spacing-0">
            <tbody>
              {informationInput !== null &&
                informationInput !== undefined &&
                informationInput.length >= 1 &&
                Object.entries(informationInput[0]).map(([key, value], i) => {
                  return (
                    <tr className="h-16  relative" key={i}>
                      <td
                        className={`${i === 0 && 'rounded-tl-xl'} ${
                          i === informationInput.length - 1 && 'rounded-bl-xl'
                        } border border-gray-300 w-1/5 px-2 py-1 text-center`}
                      >
                        <input
                          type="text"
                          className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                          value={key.split('@;!')[0]}
                          onChange={(e) => {
                            setLoadingState(1);

                            var newItem = [...informationInput];
                            let str: string =
                              e.target.value + '@;!' + key.split('@;!')[1];
                            let newObj: informationType[] = [{}];
                            Object.keys(newItem[0]).forEach((ckey) => {
                              if (ckey === key) {
                                newObj[0][str] = newItem[0][key];
                              } else {
                                newObj[0][ckey] = newItem[0][ckey];
                              }
                            });
                            setInformationInput(newObj);
                          }}
                        />
                      </td>
                      <td
                        className={`${i === 0 && 'rounded-tr-xl'} ${
                          i === informationInput.length - 1 && 'rounded-br-xl'
                        } border h-full border-gray-300 w-4/5 px-2 pt-1`}
                      >
                        <div className="flex">
                          <input
                            type="text"
                            className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                            value={value}
                            onChange={(e) => {
                              setLoadingState(1);
                              var newItem = [...informationInput];
                              newItem[0][key] = e.target.value;
                              setInformationInput(newItem);
                            }}
                          />
                          <FaMinus
                            className="my-auto cursor-pointer h-10"
                            onClick={() => {
                              setLoadingState(1);
                              let tmpInfo: informationType[] = [
                                ...informationInput,
                              ];
                              delete tmpInfo[0][key];
                              setInformationInput(tmpInfo);
                            }}
                          />
                        </div>
                      </td>
                    </tr>
                  );
                })}
            </tbody>
          </table>
          <button
            className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block"
            onClick={() => {
              let uuid = '@;!' + self.crypto.randomUUID();
              if (informationInput) {
                let newInfo = [...informationInput];
                newInfo[0][uuid] = '';
                setInformationInput(newInfo);
                setLoadingState(1);
              }
            }}
          >
            <HiPlus className="text-white mx-auto font-bold" />
          </button>
        </div>

        {/* 관계 */}
        <div className="mt-8">
          <p className="text-xl font-extrabold">관계</p>
          <table className="text-xl border w-full border-gray-300 rounded-xl border-separate border-spacing-0">
            <tbody>
              {relationshipInput?.map((info, i) => (
                <tr className="h-16 relative" key={i}>
                  <td
                    className={`${i === 0 && 'rounded-tl-xl'} ${
                      i === relationshipInput.length - 1 && 'rounded-bl-xl'
                    } border border-gray-300 w-1/5 px-2 py-1 text-center`}
                  >
                    <input
                      type="text"
                      className={`${
                        (!relationshipInput[i].targetUUID ||
                          relationshipInput[i].targetUUID === '') &&
                        'text-neutral-400'
                      } w-full resize-none outline-none truncate my-auto text-center font-bold`}
                      value={relationshipInput[i].targetName}
                      onChange={(e) => {
                        setLoadingState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].targetName = e.target.value;
                        newItem[i].targetUUID = null;
                        if (e.target.value.length > 0) {
                          setRelationCharacterSearchInput(i);
                        } else {
                          setRelationCharacterSearchInput(-1);
                        }
                        setRelationInput(newItem);
                        setOtherCharacterNameInput(e.target.value);
                      }}
                      onClick={(e) => {
                        if (
                          relationshipInput[i].targetName !==
                          otherCharacterNameInput
                        ) {
                          setFilteredRelationCharacterData([]);
                        }

                        if (
                          !relationshipInput[i].targetUUID &&
                          relationshipInput[i].targetName.length > 0
                        ) {
                          setRelationCharacterSearchInput(i);
                          setOtherCharacterName(
                            relationshipInput[i].targetName,
                          );
                          e.stopPropagation();
                        }
                      }}
                    />
                    <div
                      className={`${styles.scroll} ${
                        relationCharacterSearchInput !== i && 'hidden'
                      } absolute w-1/5 border max-h-32 overflow-y-scroll border-gray-400 left-0 top-16 divide-y divide-gray-400 bg-white z-10`}
                    >
                      {filteredRelationCharacterData.length === 0 && (
                        <div className="px-2 py-2 hover:bg-gray-200">
                          검색 결과가 없습니다!
                        </div>
                      )}
                      {filteredRelationCharacterData.map(
                        (otherCharacter, j) => (
                          <div
                            className="flex px-2 py-2 hover:bg-gray-200 cursor-pointer"
                            onClick={() => {
                              setLoadingState(1);
                              var newItem = [...relationshipInput];
                              newItem[i].targetUUID =
                                otherCharacter.characterUUID;
                              newItem[i].targetName =
                                otherCharacter.characterName || '';
                              setRelationInput(newItem);
                              setRelationCharacterSearchInput(-1);
                              setFilteredRelationCharacterData([]);
                            }}
                            key={otherCharacter.characterUUID}
                          >
                            {!otherCharacter.characterImage ? (
                              <Image
                                src="/images/default_character.png"
                                alt="관계 캐릭터 이미지 없음"
                                priority={true}
                                className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                                height={1000}
                                width={1000}
                              />
                            ) : (
                              <Image
                                src={otherCharacter.characterImage || ''}
                                alt="관계 캐릭터 이미지"
                                priority={true}
                                className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                                height={1000}
                                width={1000}
                              />
                            )}
                            <p className="truncate">
                              {otherCharacter.characterName}
                            </p>
                          </div>
                        ),
                      )}
                    </div>
                  </td>
                  <td
                    className={`${i === 0 && 'rounded-tr-xl'} ${
                      i === relationshipInput.length - 1 && 'rounded-br-xl'
                    } border h-full border-gray-300 w-4/5 px-2 pt-1`}
                  >
                    <div className="flex">
                      <input
                        type="text"
                        className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                        value={relationshipInput[i].content}
                        onChange={(e) => {
                          setLoadingState(1);
                          var newItem = [...relationshipInput];
                          newItem[i].content = e.target.value;
                          setRelationInput(newItem);
                        }}
                      />
                      <FaMinus
                        className="my-auto cursor-pointer h-10"
                        onClick={() => {
                          setLoadingState(1);
                          let tmpRelation = [...relationshipInput];
                          let tmp = tmpRelation.splice(i, 1);
                          setRelationInput(tmpRelation);
                        }}
                      />
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <button
            className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block"
            onClick={() => {
              let newRelation: relationshipType[] = [
                ...(relationshipInput || []),
                { targetUUID: null, targetName: '', content: '' },
              ];
              setRelationInput(newRelation);
              setLoadingState(1);
            }}
          >
            <HiPlus className="text-white mx-auto font-bold" />
          </button>
        </div>
      </div>
    </div>
  );
}
