'use client';
import CharacterCard from '@/components/CharacterCard';
import SubGroupCard from '@/components/SubGroupCard';
import { groupType } from '@/model/charactor';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/bs';

type Props = {
  params: {
    slug: string;
  };
};

export default function page({ params }: Props) {
  const [groups, setGroups] = useState<groupType[]>([
    {
      id: 'group1',
      name: '그룹 1',
      subGroups: [
        { id: 'subgroup1', name: '서브그룹 1' },
        { id: 'subgroup2', name: '서브그룹 2' },
      ],
      characters: [
        {
          id: 'character1',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '나이',
              content: '24',
            },
          ],
          relation: [],
        },
        {
          id: 'character2',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
          ],
          relation: [],
        },
        {
          id: 'character3',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
          ],
          relation: [],
        },
        {
          id: 'character4',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
          ],
          relation: [],
        },
      ],
    },
    {
      id: 'group2',
      name: '그룹 2',
      characters: [
        {
          id: 'character5',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
          ],
          relation: [],
        },
        {
          id: 'character6',
          name: '배트맨',
          image:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          summary: '',
          information: [
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
            {
              id: '',
              title: '',
              content: '',
            },
          ],
          relation: [],
        },
      ],
    },
  ]);
  
  const router = useRouter();

  return (
    <div className="ml-80 mr-60 my-20 select-none">
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
      <div>
        {/* 캐릭터 카드 그룹 */}
        <div>
          {groups.map((group, i) => (
            <div>
              <p
                className="text-2xl font-extrabold mt-14 cursor-pointer"
                onClick={() => {
                  router.push(`/character/${group.id}`);
                }}
              >
                {group.name}
              </p>

              <div className="grid b:grid-cols-1 c:grid-cols-2 d:grid-cols-3 e:grid-cols-4 f:grid-cols-5 grid-flow-row gap-4 ">
                {group.subGroups?.map((subGroup, i) => (
                  <SubGroupCard subGroup={subGroup} />
                ))}
              </div>
              <div className="grid b:grid-cols-1 c:grid-cols-2 d:grid-cols-3 e:grid-cols-4 f:grid-cols-5 grid-flow-row gap-4 ">
                {group.characters?.map((character, i) => (
                  <CharacterCard character={character} />
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
