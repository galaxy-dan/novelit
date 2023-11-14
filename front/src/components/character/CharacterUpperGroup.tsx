import { useRouter } from 'next/navigation';
import React from 'react';
type Props = {
  parentUUID: string | undefined;
  slug: string;
};
export default function CharacterUpperGroup({ parentUUID, slug }: Props) {
  const router = useRouter();
  return (
    <p
      onClick={() => {
        if (parentUUID && parentUUID !== '') {
          router.push(`/character/${slug}/${parentUUID}`);
        } else {
          router.push(`/character/${slug}`);
        }
      }}
    >
      상위 폴더로 이동
    </p>
  );
}
