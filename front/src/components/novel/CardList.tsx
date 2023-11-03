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

  const [directory, setDirectory] = useState<string>(slug);

  const { data: workspace }: UseQueryResult<Novel> = useQuery({
    queryKey: ['workspace', slug],
    queryFn: () => getWorkspace({ workspaceUUID: slug }),
    enabled: !!slug,
  });

  const { data: directoryList }: UseQueryResult<DirectoryList> = useQuery({
    queryKey: ['workspace', slug, directory],
    queryFn: () => getDirectory({ uuid: directory }),
    enabled: !!slug,
  });

  return (
    <>
      <CardListHeader title={workspace?.title ?? ''} />
      <div className="flex flex-col gap-2">
        <div className="flex flex-wrap gap-6">
          {directoryList?.directories?.map((el, index) => (
            <div onClick={() => setDirectory(el.uuid)}>
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
    </>
  );
}
