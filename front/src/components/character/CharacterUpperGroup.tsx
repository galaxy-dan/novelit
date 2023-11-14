import { useRouter } from 'next/navigation';
import React from 'react';
import { FaFolderTree } from 'react-icons/fa6';
type Props = {
  parentUUID: string | undefined;
  slug: string;
};
export default function CharacterUpperGroup({ parentUUID, slug }: Props) {
  const router = useRouter();
  return (
    <div
      className="flex items-center font-bold mb-4 ml-2 cursor-pointer"
      onClick={() => {
        if (parentUUID && parentUUID !== '') {
          router.push(`/character/${slug}/${parentUUID}`);
        } else {
          router.push(`/character/${slug}`);
        }
      }}
    >
      <FaFolderTree />
      <p className="ml-2">상위 항목으로 이동</p>
    </div>
  );
}
