import React from 'react';
import { subGroupType } from '@/model/charactor';
import SubGroupCard from './SubGroupCard';
type Props = {
  groups: subGroupType[];
  slug: string;
};
export default function GroupCardGroup({ groups, slug }: Props) {
  return (
    <div className="flex flex-wrap gap-4 ">
      {groups?.map(
        (group, i) =>
          !group.deleted && (
            <SubGroupCard subGroup={group} slug={slug} key={group.groupUUID} />
          ),
      )}
    </div>
  );
}
