'use client';

import Link from 'next/link';
import Card from './Card';
import { useParams } from 'next/navigation';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import { Novel, Novels } from '@/model/workspace';
import { getWorkspace } from '@/service/api/workspace';
import CardListHeader from './CardListHeader';
import { useState } from 'react';
import { getDirectory } from '@/service/api/novel';
import { Directory, DirectoryList } from '@/model/novel';

export default function CardList() {
  const searchParams = useParams();
  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const [directory, setDirectory] = useState<string[]>([slug]);
  const [directoryName, setDirectoryName] = useState<string[]>(['']);

  const { data: workspace }: UseQueryResult<Novel> = useQuery({
    queryKey: ['workspace', slug],
    queryFn: () => getWorkspace({ workspaceUUID: slug }),
    enabled: !!slug,
  });

  const { data: directoryList }: UseQueryResult<DirectoryList> = useQuery({
    queryKey: ['workspace', slug, directory[directory.length - 1]],
    queryFn: () => getDirectory({ uuid: directory[directory.length - 1] }),
    enabled: !!slug && directory.length > 0,
  });

  return (
    <>
      <CardListHeader
        title={workspace?.title ?? ''}
        parentUUID={directory[directory.length - 1]}
        directoryName={directoryName[directoryName.length - 1]}
      />
      {directory.length > 1 && (
        <button
          className="border-2 px-2 py-1 rounded-lg my-4"
          onClick={() => {
            if (directory.length <= 1) return;
            setDirectory((prev) => {
              const newData = [...prev];
              newData.pop();
              return newData;
            });

            setDirectoryName((prev) => {
              const newData = [...prev];
              newData.pop();
              return newData;
            });
          }}
        >
          뒤로 가기
        </button>
      )}

      {directoryList?.directories.length == 0 &&
      directoryList.files.length == 0 ? (
        <div>현재 폴더 안에 파일이 없습니다!</div>
      ) : (
        <div className="flex flex-col gap-2">
          <div className="flex flex-wrap gap-6">
            {directoryList?.directories?.map((el, index) => (
              <div
                className="cursor-pointer"
                onClick={() => {
                  setDirectory((prev) => [...prev, el.uuid]);
                  setDirectoryName((prev) => [...prev, el.name]);
                }}
                key={el.uuid}
              >
                <Card subject={`${el.name}`} isDirectory={true} />
              </div>
            ))}
          </div>
          <div className="flex flex-wrap gap-6">
            {directoryList?.files?.map((el, index) => (
              <Link key={index} href={`/editor/${slug}/${el.uuid}`}>
                <Card subject={`${el.name}`} isDirectory={false} />
              </Link>
            ))}
          </div>
        </div>
      )}
    </>
  );
}
