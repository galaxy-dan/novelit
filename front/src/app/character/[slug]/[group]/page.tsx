'use client';
import CharacterCardGroup from '@/components/character/CharacterCardGroup';
import CharacterHomeTitle from '@/components/character/CharacterHomeTitle';
import CharacterNameSearch from '@/components/character/CharacterNameSearch';
import CharacterUpperGroup from '@/components/character/CharacterUpperGroup';
import GroupCardGroup from '@/components/character/GroupCardGroup';
import GroupName from '@/components/character/GroupName';
import { groupItemType } from '@/model/charactor';
import { postCharacter } from '@/service/api/character';
import { getSubGroupAndCharacter, postGroup } from '@/service/api/group';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import { MdGroupAdd, MdPersonAdd } from 'react-icons/md';
import { v4 as uuidv4 } from 'uuid';

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
    queryFn: () =>
      getSubGroupAndCharacter({ workspace: params.slug, uuid: params.group }),
    onSuccess: (data) => {
      setGroupNameInput(data.groupName || '');
    },
    onError: () => {
      router.push(`/character/${params.slug}`);
    },
    staleTime: 0,
  });

  const postCharacterMutate = useMutation({
    mutationFn: postCharacter,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const postGroupMutate = useMutation({
    mutationFn: postGroup,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const [groupNameInput, setGroupNameInput] = useState<string>('');

  return (
    <div className="ml-14 my-20 select-none">
      {/* 제목 */}
      <CharacterUpperGroup
        parentUUID={groupData?.parentGroupUUID}
        slug={params.slug}
      />
      <CharacterHomeTitle slug={params.slug} />
      {/* 검색 창 */}
      <div className="flex justify-between w-full">
        <CharacterNameSearch slug={params.slug} />
        <div className="flex mt-11 mr-[10rem]">
          <div
            onClick={() => {
              const uuid = uuidv4();
              postGroupMutate.mutate({
                workspaceUUID: params.slug,
                groupName: '새 그룹',
                parentGroupUUID: params.group,
                groupUUID: uuid,
              });
            }}
            className="flex items-center bg-neutral-200 hover:bg-neutral-400 rounded-md w-fit px-2 py-1 text-2xl cursor-pointer font-bold mr-2"
          >
            <MdGroupAdd className="mr-2" />새 그룹
          </div>
          <div
            onClick={() => {
              const uuid = uuidv4();
              postCharacterMutate.mutate({
                workspace: params.slug,
                group: params.group,
                name: '새 캐릭터',
                uuid: uuid,
              });
            }}
            className="flex items-center bg-neutral-200 hover:bg-neutral-400 rounded-md w-fit px-2 py-1 text-2xl cursor-pointer font-bold"
          >
            <MdPersonAdd className="mr-2" />새 캐릭터
          </div>
        </div>
      </div>
      {/* 캐릭터 카드 전체 모음 */}
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <GroupName
          groupUUID={params.group}
          groupName={groupNameInput}
          setGroupName={setGroupNameInput}
          slug={params.slug}
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
