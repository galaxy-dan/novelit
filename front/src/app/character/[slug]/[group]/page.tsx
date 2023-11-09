'use client';
import CharacterCard from '@/components/character/CharacterCard';
import SubGroupCard from '@/components/character/SubGroupCard';
import { groupItemType, groupType } from '@/model/charactor';
import { getSubGroupAndCharacter, patchGroup } from '@/service/api/group';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/bs';

type Props = {
  params: {
    slug: string;
    group: string;
  };
};

export default function page({ params }: Props) {
  const router = useRouter();
  const queryClient = useQueryClient();

  const { data: groupData }: UseQueryResult<groupItemType> = useQuery({
    queryKey: ['group', params.group],
    queryFn: () => getSubGroupAndCharacter(params.group),
    onSuccess: (data) => setGroupNameInput(data.name),
    onError: () => {
      router.push(`/plot/${params.slug}`);
    },
  });

  const patchMutate = useMutation({
    mutationFn: () => patchGroup(params.group, groupNameInput),
    onError: () => queryClient.refetchQueries(['group', params.group]),
  });

  const [groupNameInput, setGroupNameInput] = useState<string>('');

  const [width, setWidth] = useState(100);
  const characterNameRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (characterNameRef !== null && characterNameRef.current !== null) {
      if (characterNameRef.current.offsetWidth > 100) {
        setWidth(characterNameRef.current.offsetWidth + 5);
      } else {
        setWidth(100);
      }
    }

    const debounce = setTimeout(() => {
      patchMutate.mutate();
    }, 1300);
    return () => {
      clearTimeout(debounce);
    };
  }, [groupNameInput]);

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
          <span
            ref={characterNameRef}
            className="invisible opacity-0 absolute text-4xl font-extrabold"
          >
            {groupNameInput}
          </span>
          <input
            className="text-4xl font-extrabold max-w-[30rem] truncate"
            style={{ width }}
            type="text"
            onChange={(e) => {
              setGroupNameInput(e.target.value);
            }}
            value={groupNameInput}
          />
        </div>

        <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
          {groupData?.childGroup?.map((group, i) => (
            <SubGroupCard subGroup={group} slug={params.slug} key={group.id} />
          ))}
        </div>
        <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
          {groupData?.childCharacter?.map((character, i) => (
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
