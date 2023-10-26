import CharacterCard from '@/components/CharacterCard';
import React from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/bs';
export default function page() {
  return (
    <div className="mx-60 mt-20">
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
          <div className="grid md:grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 grid-flow-row gap-4 ">
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
