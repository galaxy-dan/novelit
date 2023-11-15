import { patchGroup } from '@/service/api/group';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import React, { useEffect, useRef, useState } from 'react';
type Props = {
  groupUUID: string;
  groupName: string;
  setGroupName: React.Dispatch<React.SetStateAction<string>>;
};
export default function GroupName({
  groupUUID,
  groupName,
  setGroupName,
}: Props) {
  const queryClient = useQueryClient();
  const [fetched, setFetched] = useState<boolean>(false);

  const patchMutate = useMutation({
    mutationFn: () => patchGroup({ groupUUID: groupUUID, newName: groupName }),
    onError: () => queryClient.refetchQueries(['group', groupUUID]),
    onSuccess: () => queryClient.refetchQueries(['group']),
  });

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
      if (fetched) {
        patchMutate.mutate();
      } else {
        setFetched(true);
      }
    }, 500);
    return () => {
      clearTimeout(debounce);
    };
  }, [groupName]);

  const [inputText, setInputText] = useState<string>('');

  return (
    <div className='mt-10'>
      <span
        ref={characterNameRef}
        className="invisible opacity-0 absolute text-4xl font-extrabold"
      >
        {groupName}
      </span>
      <input
        className="text-4xl font-extrabold max-w-[30rem] truncate"
        style={{ width }}
        type="text"
        onChange={(e) => {
          setGroupName(e.target.value);
        }}
        value={groupName}
      />
    </div>
  );
}
