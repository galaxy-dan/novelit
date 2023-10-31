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
} from '@/model/editor/charactor';

type Prop = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Prop) {
  const [character, setCharacter] = useState<characterType>({
    id: '',
    name: '배트맨',
    image:
      'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
    summary: '',
    information: [
      {
        id: '',
        title: '',
        content: '',
      },
      { id: '', title: '', content: '' },
      { id: '', title: '', content: '' },
    ],
    relation: [
      { id: '', name: '', content: '' },
      { id: '', name: '', content: '' },
      { id: '', name: '', content: '' },
    ],
  });
  const [name, setName] = useState<string>('배트맨');
  const [image, setImage] = useState<string>(
    'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
  );
  const [information, setInformation] = useState<informationType[]>(
    character.information,
  );
  const [relation, setRelation] = useState<relationType[]>(character.relation);
  const [summary, setSummary] = useState<string>('');
  const imgRef = useRef<HTMLInputElement>(null);
  const [width, setWidth] = useState(100);
  const nameRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (nameRef !== null && nameRef.current !== null) {
      if (nameRef.current.offsetWidth > 100) {
        setWidth(nameRef.current.offsetWidth + 5);
      } else {
        setWidth(100);
      }
    }
  }, [character.name]);

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
              {character.name}
            </span>
            <input
              className="text-4xl font-extrabold max-w-[30rem] truncate"
              style={{ width }}
              autoFocus
              type="text"
              onChange={(e) => {
                setCharacter((prev) => ({ ...prev, name: e.target.value }));
              }}
              value={character.name}
            />
          </div>

          <FaRegTrashAlt
            className="text-2xl mb-1 cursor-pointer"
            onClick={() => {}}
          />
        </div>
        <div className="flex items-center">
          <p className="text-2xl font-extrabold mr-2">저장중</p>
          <AiOutlineLoading3Quarters className="animate-spin text-xl " />
          <AiOutlineCheck className="text-2xl" />
        </div>
      </div>

      {/* 캐릭터 이미지 및 설명 */}
      <div className="flex h-64 mt-6">
        <div className="relative w-56 h-56 mr-10 place-self-end">
          <Image
            src={character.image}
            alt="캐릭터 상세 이미지"
            priority={true}
            className="object-cover w-full h-full cursor-pointer"
            height={100}
            width={100}
            onClick={() => {
              imgRef?.current?.click();
            }}
          />
          <input
            type="file"
            className="hidden"
            ref={imgRef}
            accept="image/*"
            onChange={(e) => {
              const targetFiles = (e.target as HTMLInputElement)
                .files as FileList;
              const targetFile = targetFiles[0];
              console.log(targetFile.name);

              const yeah = async () => {
                const url = await getS3URL(targetFile.name);
                // 이후에 이미지를 S3 서버에 전송, 실패하면 백엔드에 실패 및 url 삭제 요청
                const imgUrl = url.split('?')[0];
                // 업로드 실패 시
                if (!(await uploadImage(url, targetFile))) {
                }
                console.log('전송~');
              };

              yeah();
            }}
          />
        </div>
        <div className="flex flex-col flex-grow justify-between">
          <p className="text-xl font-bold">캐릭터 설명</p>
          <textarea
            className="border-2 border-gray-300 rounded-xl resize-none outline-none h-56 px-4 py-2 font-bold text-lg"
            //value={summary}
            value={character.summary}
            onChange={(e) => {
              //setSummary(e.target.value);
              setCharacter((prev) => ({ ...prev, summary: e.target.value }));
            }}
          />
        </div>
      </div>

      {/* 기본 정보 */}
      <div className="mt-8">
        <p className="text-xl font-extrabold">기본 정보</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            {information.map((info, i) => (
              <tr className="h-16" key={i}>
                <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    //value={information[i].title}
                    value={character.information[i].title}
                    onChange={(e) => {
                      var newItem = [...information];
                      newItem[i].title = e.target.value;
                      //setInformation(newItem);
                      setCharacter((prev) => ({
                        ...prev,
                        information: newItem,
                      }));
                    }}
                  />
                </td>
                <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
                  <div className="flex">
                    <input
                      type="text"
                      className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                      //value={information[i].content}
                      value={character.information[i].content}
                      onChange={(e) => {
                        var newItem = [...information];
                        newItem[i].content = e.target.value;
                        //setInformation(newItem);
                        setCharacter((prev) => ({
                          ...prev,
                          information: newItem,
                        }));
                      }}
                    />
                    <FaMinus
                      className="my-auto cursor-pointer h-10"
                      onClick={() => {}}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block">
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>

      {/* 관계 */}
      <div className="mt-8">
        <p className="text-xl font-extrabold">관계</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            {relation.map((info, i) => (
              <tr className="h-16" key={i}>
                <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    //value={info.name}
                    value={character.relation[i].name}
                    onChange={(e) => {
                      var newItem = [...relation];
                      newItem[i].name = e.target.value;
                      //setRelation(newItem);
                      setCharacter((prev) => ({ ...prev, ralation: newItem }));
                    }}
                  />
                </td>
                <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
                  <div className="flex">
                    <input
                      type="text"
                      className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                      //value={relation[i].content}
                      value={character.relation[i].content}
                      onChange={(e) => {
                        var newItem = [...relation];
                        newItem[i].content = e.target.value;
                        //setRelation(newItem);
                        setCharacter((prev) => ({
                          ...prev,
                          ralation: newItem,
                        }));
                      }}
                    />
                    <FaMinus
                      className="my-auto cursor-pointer h-10"
                      onClick={() => {}}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block">
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>
    </div>
  );
}
