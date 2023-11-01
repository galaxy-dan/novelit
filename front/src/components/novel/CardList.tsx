'use client';

import Link from 'next/link';
import Card from './Card';
import { useParams } from 'next/navigation';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import { Directory, Novel, Novels } from '@/model/workspace';
import { getWorkspace } from '@/service/api/workspace';

export default function CardList() {
  const searchParams = useParams();
  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const { data: workspace }: UseQueryResult<Novel> = useQuery({
    queryKey: ['workspace'],
    queryFn: () => getWorkspace({ workspaceUUID: slug }),
    enabled: !!slug,
  });

  return (
    <>
      <div className="font-extrabold text-4xl my-32">{workspace?.title}</div>
      <div className="flex flex-wrap gap-6">
        {workspace?.directories?.map((el: Novels, index: number) => (
          <Link key={index} href={`/editor/${slug}/${el.id}`}>
            <Card subject={`${el.name}`} />
          </Link>
        ))}
      </div>
    </>
  );
}
