import React from 'react';
import { AiOutlineCheck, AiOutlineLoading3Quarters } from 'react-icons/ai';
import Image from 'next/image';
type Props = {
  state: number;
};
const text = ['입력전', '입력중', '저장중', '저장완료'];
export default function UploadState({ state }: Props) {
  return (
    <div className='flex items-center'>
      <p className="text-2xl font-extrabold mr-2">{text[state]}</p>

      {state === 1 && (
        <Image
        src="/images/Typing.gif"
        alt="로딩이미지"
        width={100}
        height={100}
        className={`h-[0.37rem] w-5`}
      />
      )}
      {state === 2 && (
        <AiOutlineLoading3Quarters className="animate-spin text-xl " />
      )}
      {state === 3 && <AiOutlineCheck className="text-2xl" />}

    </div>
  );
}
