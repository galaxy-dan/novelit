import { useRouter } from 'next/navigation';
import React from 'react';
import { BsFillPersonFill } from 'react-icons/bs';
type Props = {
  slug: string;
};
export default function CharacterHomeTitle({ slug }: Props) {
  const router = useRouter();
  return (
    <div className="flex items-end text-5xl">
      <BsFillPersonFill className="mr-2" />
      <p className="text-3xl font-extrabold mr-4">캐릭터 설정</p>
      <button
        className="px-2 text-xl font-extrabold border bg-neutral-200 hover:bg-neutral-400 rounded-md"
        onClick={() => {
          router.push(`/character/${slug}/relationship`);
        }}
      >
        캐릭터 관계도
      </button>
    </div>
  );
}
