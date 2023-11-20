'use client';
import { useRef, useState } from 'react';
import { NodeApi, NodeRendererProps, Tree, TreeApi } from 'react-arborist';
import { BiSolidHome } from 'react-icons/bi';
import { FiChevronsLeft } from 'react-icons/fi';
import { AiFillFolderAdd, AiFillFileAdd } from 'react-icons/ai';
import { MdEdit } from 'react-icons/md';
import { RxCross2 } from 'react-icons/rx';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { getWorkspace } from '@/service/api/workspace';
import { Novel } from '@/model/workspace';
import { useParams, useRouter } from 'next/navigation';
import { v4 as uuidv4 } from 'uuid';
import { AiOutlineMenu } from 'react-icons/ai';
import { characterDirectory } from '@/model/charactor';
import {
  deleteCharacter,
  getCharacterDirectory,
  patchCharacterName,
  postCharacter,
} from '@/service/api/character';
import { deleteGroup, patchGroup, postGroup } from '@/service/api/group';
import { useRecoilState } from 'recoil';
import { menuOpenState } from '@/store/menu';
import SideMenuMoveButton from './SideMenuMoveButton';
import { isMovableState } from '@/store/state';

export default function SideMenuCharacter() {
  const [isOpen, setIsOpen] = useRecoilState<boolean>(menuOpenState);

  const treeRef = useRef<any>(null);
  const [term, setTerm] = useState<string>('');
  const queryClient = useQueryClient();
  const router = useRouter();
  const searchParams = useParams();

  const [render, setRender] = useState<boolean>(true);

  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const { data: characters }: UseQueryResult<characterDirectory> = useQuery({
    queryKey: ['characterDirectory', slug],
    onSuccess: (data) => {
      setRender(false);
      setTimeout(() => {
        setRender(true);
      }, 1);
    },
    queryFn: () => getCharacterDirectory(slug),
    enabled: !!slug,
    staleTime: 0,
  });

  const { data: workspace }: UseQueryResult<Novel> = useQuery({
    queryKey: ['workspace', slug],
    queryFn: () => getWorkspace({ workspaceUUID: slug }),
    enabled: !!slug,
  });
  const [isMovable, setIsMovable] = useRecoilState<boolean>(isMovableState);
  return (
    <>
      {!isOpen ? (
        <button
          className="fixed top-5 left-5"
          onClick={() => setIsOpen((prev) => !prev)}
        >
          <AiOutlineMenu size={25} />
        </button>
      ) : (
        <div className="h-screen z-50 left-0 top-0 bg-violet-50 w-[260px] font-melody">
          <div className="h-full">
            <div className="flex justify-between items-center pt-4 px-4 border-b-2 border-gray-300">
              <div className="flex gap-2 items-end">
                <button
                  className="pb-4"
                  onClick={() => {
                    if (
                      isMovable ||
                      confirm('기록이 저장되지 않을 수 있습니다.')
                    ) {
                      router.push('/main');
                    }
                  }}
                >
                  <BiSolidHome size={30} />
                </button>
                <p className="font-bold text-xl pb-3">{workspace?.title}</p>
              </div>
              <button
                className=" pb-3"
                onClick={() => setIsOpen((prev) => !prev)}
              >
                <FiChevronsLeft size={20} />
              </button>
            </div>
            <div className="flex bg-violet-50">
              <SideMenuMoveButton slug={slug} />
              <div className="p-2">
                <div className="flex justify-between items-center p-1">
                  <div className="font-bold text-base flex items-center gap-2">
                    <div>👨‍👩‍👦</div>
                    <div className="pb-1">캐릭터작성</div>
                  </div>
                  <div className="flex items-center">
                    <button
                      className="py-1 hover:text-gray-600 hover:bg-gray-300"
                      onClick={() => {
                        treeRef.current.createLeaf(treeRef.current.isSelected);
                      }}
                    >
                      <AiFillFileAdd size={19.5} className="" />
                    </button>
                    <button
                      className="hover:text-gray-600 hover:bg-gray-300"
                      onClick={() => {
                        treeRef.current.createInternal(treeRef.current.root.id);
                      }}
                    >
                      <AiFillFolderAdd size={25} className="" />
                    </button>
                  </div>
                </div>
                <input
                  type="text"
                  placeholder="Search..."
                  className="py-0 px-2 outline-none rounded-sm w-full h-6 border-none my-1 focus:border-2 focus:border-solid focus:border-gray-300"
                  value={term}
                  onChange={(e) => setTerm(e.target.value)}
                />
                {characters && render && (
                  <Tree
                    ref={treeRef}
                    initialData={characters?.children || []}
                    openByDefault={true}
                    width={200}
                    height={500}
                    indent={14}
                    rowHeight={30}
                    paddingTop={15}
                    paddingBottom={10}
                    className={`scrollbar-hide`}
                    searchTerm={term}
                    searchMatch={(node, term) =>
                      node.data.name.toLowerCase().includes(term.toLowerCase())
                    }
                  >
                    {Node}
                  </Tree>
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

function Node({ node, style, dragHandle, tree }: NodeRendererProps<any>) {
  const searchParams = useParams();
  const queryClient = useQueryClient();
  const router = useRouter();
  const [isMovable, setIsMovable] = useRecoilState<boolean>(isMovableState);
  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const postCharacterMutate = useMutation({
    mutationFn: postCharacter,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const postGroupMutate = useMutation({
    mutationFn: postGroup,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const patchCharacterMutate = useMutation({
    mutationFn: patchCharacterName,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['character']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const patchGroupMutate = useMutation({
    mutationFn: patchGroup,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const deleteCharacterMutate = useMutation({
    mutationFn: deleteCharacter,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  const deleteGroupMutate = useMutation({
    mutationFn: deleteGroup,
    onSuccess: () => {
      queryClient.invalidateQueries(['group']);
      queryClient.invalidateQueries(['characterDirectory']);
    },
  });

  return (
    <>
      <div
        className={`flex h-full justify-between items-start text-violet-50 hover:text-black hover:bg-slate-50 ${
          node.isSelected ? 'bg-slate-50' : 'bg-violet-50'
        } select-none py-0`}
        onClick={() => {
          node.toggle();
        }}
        onDoubleClick={() => {
          if (node.isLeaf) {
            if (isMovable || confirm('기록이 저장되지 않을 수 있습니다.')) {
              router.push(`/character/${slug}/characterInfo/${node.id}`);
            }
          }
        }}
      >
        <div className="text-black">
          {node.isEditing ? (
            <input
              type="text"
              className="select-none"
              defaultValue={node.data.name}
              onFocus={(e) => e.currentTarget.select()}
              onBlur={() => {
                node.reset();
              }}
              onKeyDown={(e) => {
                if (e.key === 'Escape') {
                  node.reset();
                }
                if (e.key === 'Enter') {
                  let parentUUID =
                    node.parent?.id === '__REACT_ARBORIST_INTERNAL_ROOT__'
                      ? null
                      : node.parent?.id;
                  if (node.id.includes('simple')) {
                    // 캐릭터 생성
                    const uuid = uuidv4();
                    node.data.id = uuid;
                    if (node.isLeaf) {
                      postCharacterMutate.mutate({
                        workspace: slug,
                        group: parentUUID || null,
                        name: e.currentTarget.value,
                        uuid: uuid,
                      });
                    }
                    // 폴더 생성
                    else {
                      postGroupMutate.mutate({
                        workspaceUUID: slug,
                        groupName: e.currentTarget.value,
                        parentGroupUUID: parentUUID,
                        groupUUID: uuid,
                      });
                    }
                  } else {
                    // 캐릭터 수정
                    if (node.isLeaf) {
                      patchCharacterMutate.mutate({
                        workspace: slug,
                        name: e.currentTarget.value,
                        uuid: node.id,
                      });
                    }
                    // 그룹 수정
                    else {
                      patchGroupMutate.mutate({
                        groupUUID: node.id,
                        newName: e.currentTarget.value,
                        workspace: slug,
                      });
                    }
                  }
                  node.submit(e.currentTarget.value);
                }
              }}
              autoFocus
            />
          ) : (
            <div className="flex">
              <div style={style} className="text-sm font-bold">
                {node.isLeaf ? '📖' : node.isOpen ? '📂' : '📁'}
              </div>
              <div className="text-sm font-bold">{node.data.name}</div>
            </div>
          )}
        </div>

        <div className={`file-actions flex items-center`}>
          <div className="folderFileActions flex items-center">
            <button onClick={() => node.edit()} title="Rename...">
              <MdEdit />
            </button>
            <button
              onClick={() => {
                if (node.isLeaf) {
                  deleteCharacterMutate.mutate({
                    workspace: slug,
                    uuid: node.id,
                  });
                  tree.delete(node.id);
                } else {
                  deleteGroupMutate.mutate({ workspace: slug, uuid: node.id });
                  tree.delete(node.id);
                }
              }}
              title="Delete"
            >
              <RxCross2 />
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
