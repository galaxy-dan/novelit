'use client';

import React, { useEffect, useRef, useState } from 'react';
import { FaRegTrashAlt, FaMinus } from 'react-icons/fa';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/ai';
import { HiPlus } from 'react-icons/hi';
import Image from 'next/image';
import { getS3URL, uploadImage } from '@/service/character/image';
import {
  characterType,
  informationType,
  relationType,
} from '@/model/charactor';
import UploadState from '@/components/UploadState';

type Props = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Props) {
  const [character, setCharacter] = useState<characterType>({
    id: '',
    name: '배트맨',
    image:
      'https://novelit.s3.ap-northeast-2.amazonaws.com/0914cfe0-38e5-4a0d-a196-46ed011e6ff5%EB%AC%B4%EC%A0%9C.png',
    summary: '',
    information: [
      { id: '', title: '', content: '' },
      { id: '', title: '', content: '' },
      { id: '', title: '', content: '' },
    ],
    relation: [
      { id: '', name: '', content: '' },
      { id: '', name: '', content: '' },
      { id: '', name: '', content: '' },
    ],
  });

  const [imageInput, setImageInput] = useState<string>(character.image);
  const [imageUrl, setImageUrl] = useState<string>(character.image);
  const imgRef = useRef<HTMLInputElement>(null);
  const [lastUploadTime, setLastUploadTime] = useState<number>(0);
  const uploadInterval = 3000;

  const [width, setWidth] = useState(100);
  const nameRef = useRef<HTMLInputElement>(null);
  const [nameInput, setNameInput] = useState<string>(character.name);

  const [summaryInput, setSummaryInput] = useState<string>(character.summary);

  const [informationInput, setInformationInput] = useState<informationType[]>(
    character.information,
  );

  const [relationInput, setRelationInput] = useState<relationType[]>(
    character.relation,
  );

  const [state, setState] = useState<number>(0);

  const hello = () => {
    setCharacter((prev) => ({
      ...prev,
      name: nameInput,
      summary: summaryInput,
      image: imageInput,
      relation: relationInput,
      information: informationInput,
    }));
    console.log(character);
    setState(2);
  };
  useEffect(() => {
    const debounce = setTimeout(() => {
      return hello();
    }, 1300);
    return () => {
      clearTimeout(debounce);
    };
  }, [nameInput, summaryInput, imageInput, informationInput, relationInput]);

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
  return (
    <div className="mx-80 my-20 select-none">
      {/* 상단 타이틀 메뉴 + 로딩 상태 */}
      <div className="flex items-end justify-between">
        <div className="flex items-end">
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
          <Image
            src={imageUrl}
            alt="캐릭터 상세 이미지"
            priority={true}
            className="object-cover w-full h-full cursor-pointer"
            height={100}
            width={100}
            onClick={() => {
              imgRef?.current?.click();
            }}
            loader={loaderProp}
            unoptimized={true}
          />
          <input
            type="file"
            className="hidden"
            ref={imgRef}
            accept="image/*"
            onChange={(e) => {
              const now = Date.now();

              if (now - lastUploadTime < uploadInterval) {
                alert('이미지 업로드 간격이 너무 짧아요!');
                return;
              } else {
                console.log(
                  now + ' / ' + lastUploadTime + ' / ' + uploadInterval,
                );
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
                    console.log(
                      now + ' / ' + lastUploadTime + ' / ' + uploadInterval,
                    );
                  }
                };
                yeah();
              }
            }}
          />
        </div>
        <div className="flex flex-col flex-grow justify-between">
          <p className="text-xl font-bold">캐릭터 설명</p>
          <textarea
            className="border-2 border-gray-300 rounded-xl resize-none outline-none h-56 px-4 py-2 font-bold text-lg"
            value={summaryInput}
            onChange={(e) => {
              setState(1);
              setSummaryInput(e.target.value);
            }}
          />
        </div>
      </div>

      {/* 기본 정보 */}
      <div className="mt-8">
        <p className="text-xl font-extrabold">기본 정보</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            {informationInput.map((info, i) => (
              <tr className="h-16" key={i}>
                <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
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
                <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
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
                        let newInfo : informationType[] = [...character.information].splice(i, 1);
                        setInformationInput(newInfo);
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
            let newInfo : informationType[] = [
              ...character.information,
              { id: '', title: '', content: '' },
            ];
            setInformationInput(newInfo);
          }}
        >
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>

      {/* 관계 */}
      <div className="mt-8">
        <p className="text-xl font-extrabold">관계</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            {relationInput.map((info, i) => (
              <tr className="h-16" key={i}>
                <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    value={relationInput[i].name}
                    onChange={(e) => {
                      setState(1);
                      var newItem = [...relationInput];
                      newItem[i].name = e.target.value;
                      setRelationInput(newItem);
                    }}
                  />
                </td>
                <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
                  <div className="flex">
                    <input
                      type="text"
                      className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                      value={relationInput[i].content}
                      onChange={(e) => {
                        setState(1);
                        var newItem = [...relationInput];
                        newItem[i].content = e.target.value;
                        setRelationInput(newItem);
                      }}
                    />
                    <FaMinus
                      className="my-auto cursor-pointer h-10"
                      onClick={() => {
                        let newRelation : relationType[] = [...character.relation].splice(i, 1);
                        setRelationInput(newRelation);
                      }}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block"
        onClick={() => {
          let newRelation : relationType[] = [
            ...character.relation,
            { id: '', name: '', content: '' },
          ];
          setRelationInput(newRelation);
        }}>
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>
    </div>
  );
}
