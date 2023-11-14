'use client';
import CharacterCardGroup from '@/components/character/CharacterCardGroup';
import CharacterHomeTitle from '@/components/character/CharacterHomeTitle';
import CharacterNameSearch from '@/components/character/CharacterNameSearch';
import CharacterUpperGroup from '@/components/character/CharacterUpperGroup';
import GroupCardGroup from '@/components/character/GroupCardGroup';
import GroupName from '@/components/character/GroupName';
import { groupItemType } from '@/model/charactor';
import { getSubGroupAndCharacter } from '@/service/api/group';
import {
  UseQueryResult,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';

type Props = {
  params: {
    slug: string;
    group: string;
  };
};

export default function page({ params }: Props) {
  const router = useRouter();
  const queryClient = useQueryClient();
  const [fetched, setFetched] = useState<boolean>(false);
  const { data: groupData }: UseQueryResult<groupItemType> = useQuery({
    queryKey: ['group', params.group],
    queryFn: () => getSubGroupAndCharacter(params.group),
    onSuccess: (data) => {
      setGroupNameInput(data.groupName || '');
    },
    onError: () => {
      router.push(`/character/${params.slug}`);
    },
    staleTime: 0,
  });

  const [groupNameInput, setGroupNameInput] = useState<string>('');

  return (
    <div className="ml-10 my-20 select-none">
      {/* 제목 */}
      <CharacterUpperGroup
        parentUUID={groupData?.parentGroupUUID}
        slug={params.slug}
      />
      <CharacterHomeTitle slug={params.slug} />
      {/* 검색 창 */}
      <CharacterNameSearch slug={params.slug} />
      {/* 캐릭터 카드 전체 모음 */}
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <GroupName
          groupUUID={params.group}
          groupName={groupNameInput}
          setGroupName={setGroupNameInput}
        />

        <GroupCardGroup slug={params.slug} groups={groupData?.groups || []} />
        <CharacterCardGroup
          slug={params.slug}
          characters={groupData?.characters || []}
        />
      </div>
    </div>
  );
}
