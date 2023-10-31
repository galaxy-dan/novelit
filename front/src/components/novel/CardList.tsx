'use client';

import Link from 'next/link';
import Card from './Card';
import { useParams } from 'next/navigation';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import { Directory } from '@/model/workspace';
import { getWorkspace } from '@/service/api/workspace';

export default function CardList() {
  const searchParams = useParams();
  console.log(searchParams.slug);

  const { data: workspace }: UseQueryResult<Directory> = useQuery({
    queryKey: ['workspace'],
    queryFn: () => getWorkspace({ workspaceUUID: searchParams.slug }),
    enabled: !!searchParams.slug,
  });

  return (
    <>
      <div className="font-extrabold text-4xl my-32">{workspace?.title}</div>
      <div className="flex flex-col gap-20">
        {[0, 1, 2].map((el, i) => (
          <div key={i} className="flex flex-col gap-4">
            <Card subject={`${i + 1}권`} />
            <div className="flex flex-wrap gap-6">
              {[0, 1, 2, 3, 4].map((elem, index) => (
                <Link key={index} href="/editor">
                  <Card subject={`${index + 1}화`} />
                </Link>
              ))}
            </div>
          </div>
        ))}
      </div>
    </>
  );
}
