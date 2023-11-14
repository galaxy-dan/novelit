import React from 'react';
import { subGroupType } from '@/model/charactor';
import SubGroupCard from './SubGroupCard';
type Props = {
  groups: subGroupType[];
  slug: string;
};
export default function GroupCardGroup({ groups, slug }: Props) {
  return (
    <div className="grid a:grid-cols-1 b:grid-cols-2 c:grid-cols-3 d:grid-cols-4 e:grid-cols-5 f:grid-cols-6 grid-flow-row gap-4 ">
      {groups?.map(
        (group, i) =>
          !group.deleted && (
            <SubGroupCard subGroup={group} slug={slug} key={group.groupUUID} />
          ),
      )}
    </div>
  );
}
