import { subGroupType } from '@/model/editor/charactor';
import { useRouter } from 'next/navigation';
import React from 'react';
type Props = {
  subGroup: subGroupType;
};
export default function SubGroupCard({ subGroup }: Props) {

  const router = useRouter();

  return (
    <div
      className="flex border-2 rounded-md w-72 h-40 px-3 items-center shadow-lg mt-8 cursor-pointer"
      onClick={() => {
        router.push(`/character/${subGroup.id}`);
      }}
    >
      <div className="ml-4 w-36 my-auto">
        <p className="font-extrabold text-2xl">{subGroup.name}</p>
      </div>
    </div>
  );
}
