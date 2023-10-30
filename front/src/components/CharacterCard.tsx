import React from 'react';
import Image from 'next/image';

export default function CharacterCard() {
  return (
    <div className="flex border-2 rounded-md w-72 h-40 px-3 items-center shadow-lg mt-8 cursor-pointer" onClick={()=>{}}>
      <Image
        src="https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8"
        width={100}
        height={100}
        alt="캐릭터이미지"
        className="w-24 h-32 object-cover"
      />

      <div className="ml-4 w-36 my-auto">
        <p className="font-extrabold text-xl">배트맨</p>
        <div className="font-bold">
          <div className="flex mt-1 min-w-0">
            <p className="mr-2">나이:</p>
            <p className="flex-1 truncate">aaaaaaaaaaaaaaaaaaaaaaaa</p>
          </div>
          <div className="flex mt-1 min-w-0">
            <p className="mr-2">성격:</p>
            <p className="flex-1 truncate">aaaaaaaaaaaaaaaaaaaaaaaa</p>
          </div>
          <div className="flex mt-1 min-w-0">
            <p className="mr-2">상태:</p>
            <p className="flex-1 truncate">aaaaaaaaaaaaaaaaaaaaaaaa</p>
          </div>
        </div>
      </div>
    </div>
  );
}
