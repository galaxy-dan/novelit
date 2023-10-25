import CharacterCard from '@/component/CharacterCard';
import { getRedirectTypeFromError } from 'next/dist/client/components/redirect';
import Image from 'next/image';
import React from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/BS';
export default function page() {
  return (
    <div className="ml-10 mt-20">
      {/* 제목 */}
      <div className="flex items-end text-5xl">
        <BsFillPersonFill className="mr-2" />
        <p className="text-3xl">캐릭터 설정</p>
      </div>
      {/* 검색 창 */}
      <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-14 text-2xl">
        <label htmlFor="hm">
          <BsSearch />
        </label>
        <input
          id="hm"
          type="text"
          className="bg-neutral-200 rounded-md ml-2 outline-none"
        />
      </div>
      {/* 캐릭터 카드 전체 모음 */}
      <div>
        {/* 캐릭터 카드 그룹 */}
        <div>
          <p className="text-2xl font-semibold mt-14">Group 1</p>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4   grid-flow-row gap4 ">
            <CharacterCard />
            <CharacterCard />
            <CharacterCard />
            <CharacterCard />
            <CharacterCard />
            <CharacterCard />
          </div>
        </div>
      </div>
    </div>
  );
}
