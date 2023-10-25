import React from 'react';
import { FaRegTrashAlt } from 'react-icons/Fa';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/Ai';
import Image from 'next/image';

export default function page() {
  return (
    <div className="mx-10 mt-20">
      {/* 상단 타이틀 메뉴 + 로딩 상태 */}
      <div className="flex items-end justify-between">
        <div className="flex items-end">
          <p className="text-4xl font-bold mr-4">배트맨</p>
          <FaRegTrashAlt className="text-3xl mb-1" />
        </div>
        <div className="flex items-center">
          <p className="text-2xl font-bold mr-2">저장중</p>
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
          <p className="text-xl font-semibold">캐릭터 설명</p>
          <textarea className="border-2 border-gray-300 rounded-xl resize-none outline-none h-56" />
        </div>
      </div>
    </div>
  );
}
