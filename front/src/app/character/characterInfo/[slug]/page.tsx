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

type Props = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Props) {
  const [character, setCharacter] = useState<characterType>({
    characterUUID: params.slug,
    characterName: '배트맨',
    characterImage: '',
    description: '',
    information: [
      { id: '', title: '', content: '' },
      { id: '', title: '', content: '' },
      { id: '', title: '', content: '' },
    ],
    relationship: [
      { id: '', name: '', content: '', uuid: '' },
      { id: '', name: '', content: '', uuid: '' },
      { id: '', name: '', content: '', uuid: '' },
    ],
  });
  const ref = useRef<characterType | null>(null);

  const [characterImageInput, setImageInput] = useState<string | undefined>(
    character.characterImage,
  );
  const [characterImageUrl, setImageUrl] = useState<string | undefined>(
    character.characterImage,
  );
  const imgRef = useRef<HTMLInputElement>(null);
  const [lastUploadTime, setLastUploadTime] = useState<number>(0);
  const uploadInterval = 3000;

  const [width, setWidth] = useState(100);
  const nameRef = useRef<HTMLInputElement>(null);
  const [nameInput, setNameInput] = useState<string | undefined>(
    character.characterName,
  );

  const [descriptionInput, setdescriptionInput] = useState<string | undefined>(
    character.description,
  );

  const [informationInput, setInformationInput] = useState<
    informationType[] | undefined
  >(character.information);

  const [relationshipInput, setRelationInput] = useState<
    relationshipType[] | undefined
  >(character.relationship);

  const [searchInput, setSearchInput] = useState<number>(-1);

  const [state, setState] = useState<number>(0);

  const hello = () => {
    setCharacter((prev) => ({
      ...prev,
      characterName: nameInput,
      description: descriptionInput,
      characterImage: characterImageInput,
      relationship: relationshipInput,
      information: informationInput,
    }));
  };

  useEffect(() => {
    const debounce = setTimeout(() => {
      return hello();
    }, 1300);
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
      //변한 부분이 있으면 upload
      setState(2);
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
    }
  }, [nameInput]);

  const loaderProp = ({ src }: any) => {
    return src;
  };

  window.onbeforeunload = function(e) {
    // 입력 중이거나 저장 중일때는 나갈지 묻는다.
    if( state!== 1 && state !== 2 ) {
      return;
    }
    //메시지는 사용할 수 없다. 커스텀 메세지를 막아놓음..
    var dialogText = '아직 저장이 완료되지 않았습니다. 페이지를 정말로 이동하시겠습니까?';
    e.returnValue = dialogText;
    return dialogText;
  };

  return (
    <div className="px-80 py-20 select-none" onClick={() => setSearchInput(-1)}>
      {/* 상단 타이틀 메뉴 + 로딩 상태 */}
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
                setState(1);
              }}
              value={nameInput}
            />
          </div>

          <FaRegTrashAlt
            className="text-2xl mb-1 cursor-pointer"
            onClick={() => {}}
          />
        </div>

        <UploadState state={state} />
      </div>
      {/* 캐릭터 이미지 및 설명 */}
      <div className="flex h-64 mt-6">
        <div className="relative w-56 h-56 mr-10 place-self-end">
          {characterImageUrl !== '' && characterImageUrl !== undefined ? (
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
              }

              setState(1);

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
            }}
          />
        </div>
        <div className="flex flex-col flex-grow justify-between">
          <p className="text-xl font-bold">캐릭터 설명</p>
          <div className="border-2 border-gray-300 rounded-xl h-56 p-2">
            <textarea
              className={`${styles.scroll} resize-none outline-none font-bold text-lg w-full h-full`}
              value={descriptionInput}
              onChange={(e) => {
                setState(1);
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
            {informationInput?.map((info, i) => (
              <tr className="h-16  relative" key={i}>
                <td
                  className={`${i === 0 && 'rounded-tl-xl'} ${
                    i === informationInput.length - 1 && 'rounded-bl-xl'
                  } border border-gray-300 w-1/5 px-2 py-1 text-center`}
                >
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    value={informationInput[i].title}
                    onChange={(e) => {
                      setState(1);
                      var newItem = [...informationInput];
                      newItem[i].title = e.target.value;
                      setInformationInput(newItem);
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
                      value={informationInput[i].content}
                      onChange={(e) => {
                        setState(1);
                        var newItem = [...informationInput];
                        newItem[i].content = e.target.value;
                        setInformationInput(newItem);
                      }}
                    />
                    <FaMinus
                      className="my-auto cursor-pointer h-10"
                      onClick={() => {
                        setState(1);
                        let tmpInfo = [...informationInput];
                        let tmp = tmpInfo.splice(i, 1);
                        setInformationInput(tmpInfo);
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
            let newInfo: informationType[] = [
              ...(informationInput || []),
              { id: '', title: '', content: '' },
            ];
            setInformationInput(newInfo);
            setState(1);
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
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    value={relationshipInput[i].name}
                    onChange={(e) => {
                      setState(1);
                      var newItem = [...relationshipInput];
                      newItem[i].name = e.target.value;
                      setRelationInput(newItem);
                      setSearchInput(i);
                      //UUID null로 초기화
                    }}
                  />
                  <div
                    className={`${styles.scroll} ${
                      searchInput !== i && 'hidden'
                    } absolute w-1/5 border h-32 overflow-y-scroll border-gray-400 left-0 top-16 divide-y divide-gray-400 bg-white z-10`}
                  >
                    <div
                      className="flex px-2 py-2 hover:bg-gray-200 cursor-pointer"
                      onClick={() => {
                        setState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].name = '이름이름이름이름';
                        setRelationInput(newItem);
                        setSearchInput(-1);
                        //UUID
                      }}
                    >
                      <Image
                        src="/characterImages/default_character.png"
                        alt="관계 캐릭터 이미지"
                        priority={true}
                        className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                        height={1000}
                        width={1000}
                      />
                      <p className="truncate">이름이름이름이름이름이름</p>
                    </div>
                    <div
                      className="flex px-2 py-2 hover:bg-gray-200 cursor-pointer"
                      onClick={() => {
                        setState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].name = '이름이름이름이름';
                        setRelationInput(newItem);
                        setSearchInput(-1);
                        //UUID
                      }}
                    >
                      <Image
                        src="/characterImages/default_character.png"
                        alt="관계 캐릭터 이미지"
                        priority={true}
                        className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                        height={1000}
                        width={1000}
                      />
                      <p className="truncate">이름이름이름이름이름이름</p>
                    </div>
                    <div
                      className="flex px-2 py-2 hover:bg-gray-200 cursor-pointer"
                      onClick={() => {
                        setState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].name = '이름이름이름이름';
                        setRelationInput(newItem);
                        setSearchInput(-1);
                        //UUID
                      }}
                    >
                      <Image
                        src="/characterImages/default_character.png"
                        alt="관계 캐릭터 이미지"
                        priority={true}
                        className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                        height={1000}
                        width={1000}
                      />
                      <p className="truncate">이름이름이름이름이름이름</p>
                    </div>
                    <div
                      className="flex px-2 py-2 hover:bg-gray-200 cursor-pointer"
                      onClick={() => {
                        setState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].name = '이름이름이름이름';
                        setRelationInput(newItem);
                        setSearchInput(-1);
                        //UUID
                      }}
                    >
                      <Image
                        src="/characterImages/default_character.png"
                        alt="관계 캐릭터 이미지"
                        priority={true}
                        className="object-contain w-1/6 cursor-pointer mr-1 items-center"
                        height={1000}
                        width={1000}
                      />
                      <p className="truncate">이름이름이름이름이름이름</p>
                    </div>
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
                        setState(1);
                        var newItem = [...relationshipInput];
                        newItem[i].content = e.target.value;
                        setRelationInput(newItem);
                      }}
                    />
                    <FaMinus
                      className="my-auto cursor-pointer h-10"
                      onClick={() => {
                        setState(1);
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
              { id: '', name: '', content: '' },
            ];
            setRelationInput(newRelation);
            setState(1);
          }}
        >
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>
    </div>
  );
}
