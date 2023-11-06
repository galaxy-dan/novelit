import React from 'react';
import { BsJournalBookmark } from 'react-icons/bs';

export default function PlotCard() {
  return (
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
  );
}
