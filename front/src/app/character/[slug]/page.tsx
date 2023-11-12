'use client';
import CharacterCard from '@/components/character/CharacterCard';
import SubGroupCard from '@/components/character/SubGroupCard';
import {  groupItemType } from '@/model/charactor';
import { getTopGroupAndCharacter } from '@/service/api/group';
import { UseQueryResult, useQuery, useQueryClient } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/bs';
type Props = {
  params: {
    slug: string;
  };
};
export default function page({ params }: Props) {

  const router = useRouter();
  const queryClient = useQueryClient();
  
  const { data: groupData }: UseQueryResult<groupItemType> = useQuery({
    queryKey: ['group', params.slug],
    queryFn: () => getTopGroupAndCharacter(params.slug),
    onError: () => {
      router.push(`/main`);
    },
  });

  return (
    <div className="ml-10 my-20 select-none">
      {/* 제목 */}
      <div className="flex items-end text-5xl">
        <BsFillPersonFill className="mr-2" />
        <p className="text-3xl font-extrabold">캐릭터 설정</p>
      </div>
      {/* 검색 창 */}
      <div className="flex items-center bg-neutral-200 rounded-md w-fit px-2 py-1 mt-14 text-2xl">
        <label htmlFor="hm">
          <BsSearch />
        </label>
        <input
          id="hm"
          type="text"
          className="bg-neutral-200 rounded-md ml-2 outline-none truncate font-extrabold text-xl"
        />
      </div>
      {/* 캐릭터 카드 전체 모음 */}
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <div>
          <p className="text-4xl font-extrabold max-w-[30rem] truncate">전체</p>
        </div>

        <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
          {groupData?.groups?.map((group, i) => (
            <SubGroupCard
              subGroup={group}
              slug={params.slug}
              key={group.groupUUID}
            />
          ))}
        </div>
        <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
          {groupData?.characters?.map((character, i) => (
            <CharacterCard
              character={character}
              slug={params.slug}
              key={character.characterUUID}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
