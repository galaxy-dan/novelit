'use client';

import React, { useState } from 'react';
import { FaRegTrashAlt, FaMinus } from 'react-icons/fa';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/ai';
import { HiPlus } from 'react-icons/hi';
import Image from 'next/image';

type informationType = {
  title: string;
  content: string;
};
type relationType = {
  name: string;
  content: string;
};

type characterType = {
  image: string;
  summary: string;
  information: informationType[];
  relation: relationType[];
};

export default function page() {
  const [character, setCharacter] = useState<characterType>({
    image:
      'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
    summary: '',
    information: [
      {
        title: '',
        content: '',
      },
      {
        title: '',
        content: '',
      },
      {
        title: '',
        content: '',
      },
    ],
    relation: [
      {
        name: '',
        content: '',
      },
      {
        name: '',
        content: '',
      },
      {
        name: '',
        content: '',
      },
    ],
  });

  return (
    <div className="mx-80 mt-20">
      {/* 상단 타이틀 메뉴 + 로딩 상태 */}
      <div className="flex items-end justify-between">
        <div className="flex items-end">
          <p className="text-4xl font-extrabold mr-4">배트맨</p>
          <FaRegTrashAlt className="text-2xl mb-1" />
        </div>
        <div className="flex items-center">
          <p className="text-2xl font-extrabold mr-2">저장중</p>
          <AiOutlineLoading3Quarters className="animate-spin text-xl " />
          <AiOutlineCheck className="text-2xl" />
        </div>
      </div>

      {/* 캐릭터 이미지 및 설명 */}
      <div className="flex h-64">
        <div className="relative w-56 h-56 mr-10 place-self-end">
          <Image
            src="https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8"
            alt="캐릭터 상세 이미지"
            layout="fill"
            objectFit="cover"
          />
        </div>
        <div className="flex flex-col flex-grow justify-between">
          <p className="text-xl font-bold">캐릭터 설명</p>
          <textarea
            className="border-2 border-gray-300 rounded-xl resize-none outline-none h-56 px-4 py-2 font-bold text-lg"
            value={character.summary}
            onChange={(e) => {
              setCharacter({ ...character, summary: e.target.value });
            }}
          />
        </div>
      </div>

      {/* 기본 정보 */}
      <div className="mt-8">
        <p className="text-xl font-bold">기본 정보</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            {character.information.map((info, i) => (
              <tr className="h-16">
                <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    value={info.title}
                    onChange={(e) => {
                      var newItem = [...character.information];
                      newItem[i].title = e.target.value;
                      setCharacter({ ...character, information: newItem });
                    }}
                  />
                </td>
                <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
                  <div className="flex">
                    <input
                      type="text"
                      className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                    />
                    <FaMinus className="my-auto" />
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
        <p className="text-xl font-bold">관계</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl overflow-hidden border-separate border-spacing-0">
          <tbody>
            <tr className="h-16">
              <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                <input
                  type="text"
                  className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                />
              </td>
              <td className="border h-full border-gray-300 w-4/5 px-2 pt-1">
                <div className="flex">
                  <input
                    type="text"
                    className="w-full resize-none outline-none truncate my-auto text-center font-bold"
                  />
                  <FaMinus className="my-auto" />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <button className="bg-black w-32 h-4 pt-0 rounded-b-3xl mx-auto block">
          <HiPlus className="text-white mx-auto font-bold" />
        </button>
      </div>
    </div>
  );
}
