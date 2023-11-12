'use client';
import CharacterCardGroup from '@/components/character/CharacterCardGroup';
import CharacterHomeTitle from '@/components/character/CharacterHomeTitle';
import CharacterNameSearch from '@/components/character/CharacterNameSearch';
import GroupCardGroup from '@/components/character/GroupCardGroup';
import { groupItemType } from '@/model/charactor';
import { getTopGroupAndCharacter } from '@/service/api/group';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React from 'react';
type Props = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Props) {
  const router = useRouter();

  const { data: groupData }: UseQueryResult<groupItemType> = useQuery({
    queryKey: ['group', 'root'],
    queryFn: () => getTopGroupAndCharacter(params.slug),
    onError: () => {
      router.push(`/main`);
    },
    staleTime: 0,
  });

  return (
    <div className="ml-10 my-20 select-none">
      {/* 제목 */}
      <CharacterHomeTitle slug={params.slug} />
      {/* 검색 창 */}
      <CharacterNameSearch slug={params.slug} />
      {/* 캐릭터 카드 전체 모음 */}
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <div>
          <p className="text-4xl font-extrabold max-w-[30rem] truncate">전체</p>
        </div>

        <GroupCardGroup slug={params.slug} groups={groupData?.groups || []} />
        <CharacterCardGroup
          slug={params.slug}
          characters={groupData?.characters || []}
        />
      </div>
    </div>
  );
}
