import { subGroupType } from '@/model/charactor';
import { useRouter } from 'next/navigation';
import React from 'react';
type Props = {
  subGroup: subGroupType;
  slug: string;
};
export default function SubGroupCard({ subGroup, slug }: Props) {
  const router = useRouter();

  return (
    <div
      className="flex border-2 rounded-md w-72 h-40 px-3 items-center shadow-lg mt-8 cursor-pointer"
      onClick={() => {
        router.push(`/character/${slug}/${subGroup.groupUUID}`);
      }}
    >
      <div className="ml-4 w-36 my-auto">
        <p className="font-extrabold text-2xl">{subGroup.groupName}</p>
      </div>
    </div>
  );
}
