import router from 'next/router';
import React from 'react';
import { BsFillPersonFill } from 'react-icons/bs';
type Props = {
  slug: string;
};
export default function CharacterHomeTitle({ slug }: Props) {
  return (
    <div className="flex items-end text-5xl">
      <BsFillPersonFill className="mr-2" />
      <p className="text-3xl font-extrabold">캐릭터 설정</p>
    </div>
  );
}
