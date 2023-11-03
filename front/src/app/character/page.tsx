'use client';
import CharacterCard from '@/components/character/CharacterCard';
import SubGroupCard from '@/components/character/SubGroupCard';
import { characterType, groupType, subGroupType } from '@/model/charactor';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react';
import { BsFillPersonFill, BsSearch } from 'react-icons/bs';

export default function page() {
  const [subGroups, setSubgroups] = useState<subGroupType[]>([
    { id: 'subgroup1', name: '서브그룹 1' },
    { id: 'subgroup2', name: '서브그룹 2' },
  ]);
  const [characters, setCharacters] = useState<characterType[]>([
    {
      characterUUID: 'character1',
      characterName: '배트맨',
      characterImage:
        'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
      description: '',
      information: [
        {
          characterUUID: '',
          title: '나이',
          content: '24',
        },
      ],
      relationship: [],
    },
    {
      characterUUID: 'character2',
      characterName: '배트맨',
      description: '',
      information: [
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
      ],
      relationship: [],
    },
    {
      characterUUID: 'character3',
      characterName: '배트맨',
      characterImage:
        'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
      description: '',
      information: [
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
      ],
      relationship: [],
    },
    {
      characterUUID: 'character4',
      characterName: '배트맨',
      characterImage:
        'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
      description: '',
      information: [
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
        {
          characterUUID: '',
          title: '',
          content: '',
        },
      ],
      relationship: [],
    },
  ]);
  const [group, setGroup] = useState<groupType>({
    groupUUID: 'group1',
    groupName: '그룹 1',
  });

  const [width, setWidth] = useState(100);
  const groupNameRef = useRef<HTMLInputElement>(null);
  useEffect(() => {
    if (groupNameRef !== null && groupNameRef.current !== null) {
      if (groupNameRef.current.offsetWidth > 100) {
        setWidth(groupNameRef.current.offsetWidth + 5);
      } else {
        setWidth(100);
      }
    }
  }, [group.groupName]);

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
      <div className="mt-6">
        {/* 캐릭터 카드 그룹 */}
        <div>
          <span
            ref={groupNameRef}
            className="invisible opacity-0 absolute text-4xl font-extrabold"
          >
            {group.groupName}
          </span>
          <input
            className="text-4xl font-extrabold max-w-[30rem] truncate"
            style={{ width }}
            type="text"
            onChange={(e) => {
              setGroup((prev) => ({ ...prev, groupName: e.target.value }));
            }}
            value={group.groupName}
          />
        </div>

        <div className="grid b:grid-cols-1 c:grid-cols-2 d:grid-cols-3 e:grid-cols-4 f:grid-cols-5 grid-flow-row gap-4 ">
          {subGroups?.map((subGroup, i) => (
            <SubGroupCard subGroup={subGroup} />
          ))}
        </div>
        <div className="grid b:grid-cols-1 c:grid-cols-2 d:grid-cols-3 e:grid-cols-4 f:grid-cols-5 grid-flow-row gap-4 ">
          {characters?.map((character, i) => (
            <CharacterCard character={character} />
          ))}
        </div>
      </div>
    </div>
  );
}
