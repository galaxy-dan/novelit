'use client';
import { ForwardedRef, useRef, useState } from 'react';
import { NodeRendererProps, Tree, TreeApi } from 'react-arborist';

import { MdOutlineStickyNote2 } from 'react-icons/md';
import { IoExtensionPuzzle } from 'react-icons/io5';
import People from '../../public/images/people.svg';
import Image from 'next/image';
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
import { PostDirectory } from '@/model/novel';
import {
  deleteDirectory,
  patchDirectory,
  postDirectory,
} from '@/service/api/novel';
import { getWorkspace } from '@/service/api/workspace';
import { Directory, Novel } from '@/model/workspace';
import { useParams, useRouter, useSearchParams } from 'next/navigation';
import { v4 as uuidv4 } from 'uuid';
import { AiOutlineMenu } from 'react-icons/ai';

const temp = {
  name: 'root',
  toggled: true,
  children: [
    {
      name: 'parent',
      children: [{ name: 'child1' }, { name: 'child2' }],
    },
    {
      name: 'loading parent',
      loading: true,
      children: [],
    },
    {
      name: 'parent',
      children: [
        {
          name: 'nested parent',
          children: [{ name: 'nested child 1' }, { name: 'nested child 2' }],
        },
      ],
    },
  ],
};

const temp2 = [
  { id: '1', name: '역사', children: null },
  { id: '2', name: '에세이', children: [] },
  {
    id: '3',
    name: '액션',
    children: [
      { id: 'c1', name: '원피스' },
      { id: 'c2', name: '상남자' },
      { id: 'c3', name: '화산귀환' },
    ],
  },
  {
    id: '4',
    name: '로맨스',
    children: [
      { id: 'd1', name: '궁' },
      { id: 'd2', name: 'ㅇ' },
      { id: 'd3', name: 'ㄴ' },
    ],
  },
  {
    id: '5',
    name: '로맨스',
    children: [],
  },
];

export default function SideMenu() {
  const [data, setData] = useState<any>(temp);
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const treeRef = useRef<any>(null);
  const [term, setTerm] = useState<string>('');
  const queryClient = useQueryClient();

  const router = useRouter();

  const searchParams = useParams();

  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const { data: workspace }: UseQueryResult<Novel> = useQuery({
    queryKey: ['workspace', slug],
    queryFn: () => getWorkspace({ workspaceUUID: slug }),
    enabled: !!slug,
  });

  return (
    <>
      <button
        className="fixed top-2 left-2"
        onClick={() => setIsOpen((prev) => !prev)}
      >
        <AiOutlineMenu size={25} />
      </button>

      {isOpen && (
        <div className="min-h-screen z-50 fixe left-0 top-0 bg-violet-50 min-w-[220px] font-melody">
          <div>
            <div className="flex justify-between items-center p-4 border-b-2 border-gray-300">
              <div className="flex gap-2">
                <button onClick={() => router.push('/')}>
                  <BiSolidHome size={30} />
                </button>
                <div className="font-bold text-xl">{workspace?.title}</div>
              </div>
              <button onClick={() => setIsOpen((prev) => !prev)}>
                <FiChevronsLeft size={20} />
              </button>
            </div>
            <div className="flex">
              <div className="flex flex-col gap-4 border-r-2 border-gray-300 p-2">
                <MdOutlineStickyNote2 size={20} />
                <IoExtensionPuzzle size={20} />
                <Image alt="people" src={People} width={20} />
              </div>
              <div className="p-2">
                <div className="flex justify-between items-center p-1">
                  <div className="font-bold text-base flex items-center gap-2">
                    <div>📔</div>
                    <div className="pb-1">소설작성</div>
                  </div>
                  <div>
                    <button
                      onClick={() => {
                        // console.log(treeRef.current.root.id);
                        treeRef.current.createLeaf(treeRef.current.root.id);
                      }}
                    >
                      <AiFillFileAdd size={25} />
                    </button>
                    <button
                      onClick={() => {
                        treeRef.current.createInternal(treeRef.current.root.id);
                      }}
                    >
                      <AiFillFolderAdd size={25} />
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
                {workspace?.directories && (
                  <Tree
                    ref={treeRef}
                    initialData={workspace.directories}
                    openByDefault={false}
                    width={200}
                    // height={1000}
                    indent={24}
                    rowHeight={36}
                    paddingTop={30}
                    paddingBottom={10}
                    padding={25 /* sets both */}
                    className="scrollbar-hide"
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

  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const postMutate = useMutation({
    mutationFn: postDirectory,
    onSuccess: () => {
      queryClient.invalidateQueries(['workspace']);
    },
  });

  const patchMutate = useMutation({
    mutationFn: patchDirectory,
    onSuccess: () => {
      queryClient.invalidateQueries(['workspace']);
    },
  });

  const deleteMutate = useMutation({
    mutationFn: deleteDirectory,
    onSuccess: () => {
      queryClient.invalidateQueries(['workspace']);
    },
  });

  console.log(dragHandle);

  return (
    <>
      <div
        className={`flex justify-between items-center text-violet-50 hover:text-black hover:bg-slate-50 ${
          node.isSelected ? 'bg-slate-50' : 'bg-violet-50'
        }`}
        ref={dragHandle}
        onClick={() => {
          node.toggle();
        }}
        onDoubleClick={() => {
          if (node.isLeaf) {
            router.push(`/editor/${slug}/${node.id}`);
          }
        }}
      >
        <div className="text-black">
          {node.isEditing ? (
            <input
              type="text"
              defaultValue={node.data.name}
              onFocus={(e) => e.currentTarget.select()}
              onBlur={() => node.reset()}
              onKeyDown={(e) => {
                if (e.key === 'Escape') node.reset();
                if (e.key === 'Enter') {
                  if (node.id.includes('simple')) {
                    // 생성
                    const uuid = uuidv4();
                    node.data.id = uuid;

                    postMutate.mutate({
                      name: e.currentTarget.value,
                      workspaceUUID: slug,
                      directory: !node.isLeaf,
                      parentUUID:
                        node.parent?.id === '__REACT_ARBORIST_INTERNAL_ROOT__'
                          ? slug
                          : node.parent?.id,
                      uuid,
                    });
                  } else {
                    // 수정
                    patchMutate.mutate({
                      uuid: node.data.id,
                      name: e.currentTarget.value,
                    });
                  }

                  node.submit(e.currentTarget.value);
                }
              }}
              autoFocus
            />
          ) : (
            <div style={style} className="text-sm font-bold">
              {node.isLeaf ? '📖' : node.isOpen ? '📂' : '📁'}
              {node.data.name}
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
                deleteMutate.mutate({ uuid: node.id });
                tree.delete(node.id);
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
