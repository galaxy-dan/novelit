import React from 'react';
import { BsJournalBookmark, BsSearch } from 'react-icons/bs';
import { GoBook } from 'react-icons/go';

export default function page() {
  return (
    <div className="ml-10 my-20 select-none">
      {/* 제목 */}
      <div className="flex items-end text-4xl">
        <GoBook className="mr-2" />
        <p className="text-3xl font-extrabold">플롯 홈</p>
      </div>
      {/* 검색 창 */}
      <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-14 text-2xl">
        <label htmlFor="hm">
          <BsSearch />
        </label>
        <input
          id="hm"
          type="text"
          className="bg-neutral-200 rounded-md ml-2 outline-none truncate font-extrabold text-xl"
        />
      </div>
      <div className="grid b:grid-cols-1 c:grid-cols-2 d:grid-cols-3 e:grid-cols-4 f:grid-cols-5 grid-flow-row gap-4 ">
        
      <div className="flex border-2 rounded-md w-[24rem] h-40 px-3 items-center shadow-lg mt-8 cursor-pointer">
          <BsJournalBookmark className="text-[5rem]" />

          <div className="ml-4 w-72 my-auto">
            <p className="font-extrabold text-2xl">플롯 제목</p>
            <div className="font-bold ">
              <div className="flex mt-1 min-w-0">
                <p className="flex-1 line-clamp-2 text-lg">
                  ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
