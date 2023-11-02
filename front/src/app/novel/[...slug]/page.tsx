import Card from '@/components/novel/Card';
import CardList from '@/components/novel/CardList';
import { Directory } from '@/model/workspace';
import { getWorkspace } from '@/service/api/workspace';
import { UseQueryResult, useQuery } from '@tanstack/react-query';
import Link from 'next/link';
import { useParams } from 'next/navigation';

export default function NovelPage() {
  return (
    <div className="pl-48 font-melody">
      <CardList />
    </div>
  );
}
