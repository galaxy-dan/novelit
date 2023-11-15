'use client';
import { ForwardedRef, useRef, useState } from 'react';
import { NodeRendererProps, Tree, TreeApi } from 'react-arborist';

import { MdOutlineStickyNote2 } from 'react-icons/md';
import { IoExtensionPuzzle, IoReturnUpForwardSharp } from 'react-icons/io5';
import People from '../../public/images/people.svg';
import Image from 'next/image';
import { BiSolidHome } from 'react-icons/bi';
import { FiChevronsLeft } from 'react-icons/fi';
import { AiFillFileAdd } from 'react-icons/ai';
import { RxCross2 } from 'react-icons/rx';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';

import { useParams, useRouter } from 'next/navigation';
import { AiOutlineMenu } from 'react-icons/ai';
import { deletePlot, getPlotDirectory, postPlot } from '@/service/api/plot';
import { plotDirectory, plotType } from '@/model/plot';
import Link from 'next/link';
import { getWorkspace } from '@/service/api/workspace';
import { Novel } from '@/model/workspace';
import SideMenuMoveButton from './SideMenuMoveButton';
import { useRecoilState } from 'recoil';
import { menuOpenState } from '@/store/menu';

export default function SideMenuPlot() {
  const [isOpen, setIsOpen] = useRecoilState<boolean>(menuOpenState);

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

  const { data: plotDirectories }: UseQueryResult<plotDirectory> = useQuery({
    queryKey: ['plotDirectory'],
    queryFn: () => getPlotDirectory(slug),
    enabled: !!slug,
    select: (data: any) => {
      let newData: plotDirectory = {
        id: '',
        name: '플롯',
        children: data.plotInfoDtoList.map((plot: plotType) => ({
          id: plot.plotUuid,
          name: plot.plotTitle,
        })),
      };
      return newData;
    },
  });

  const createMutate = useMutation({
    mutationFn: () => postPlot({ workspaceUuid: slug, plotTitle: '새 플롯' }),
    onSuccess: () => {
      queryClient.refetchQueries(['plotDirectory']);
      queryClient.refetchQueries(['plotList']);
    },
  });
  return (
    <>
      <button
        className="fixed top-5 left-5"
        onClick={() => {
          setIsOpen((prev) => !prev);
        }}
      >
        <AiOutlineMenu size={25} />
      </button>

      {isOpen && (
        <div className="min-h-screen z-50 left-0 top-0 bg-violet-50 w-[260px] font-melody">
          <div className="h-full">
            <div className="flex justify-between items-center pt-4 px-4 border-b-2 border-gray-300">
              <div className="flex gap-2 items-end">
                <button className="pb-4" onClick={() => router.push('/main')}>
                  <BiSolidHome size={30} />
                </button>
                <div className="font-bold text-xl  pb-3">
                  {workspace?.title}
                </div>
              </div>
              <button
                className=" pb-3"
                onClick={() => setIsOpen((prev) => !prev)}
              >
                <FiChevronsLeft size={20} />
              </button>
            </div>
            <div className="flex h-full">
              <SideMenuMoveButton slug={slug} />
              <div className="p-2">
                <div className="flex justify-between items-center p-1">
                  <div className="font-bold text-base flex items-center gap-2">
                    <div>💭</div>
                    <div className="pb-1">플롯작성</div>
                  </div>
                  <div className="flex items-center">
                    <button
                      onClick={() => {
                        createMutate.mutate();
                      }}
                    >
                      <AiFillFileAdd size={19.5} />
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
                {plotDirectories?.children && (
                  <Tree
                    ref={treeRef}
                    data={plotDirectories.children}
                    openByDefault={false}
                    width={200}
                    // height={1000}
                    indent={18}
                    rowHeight={33}
                    paddingTop={15}
                    paddingBottom={10}
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

function Node({ node, style, tree }: NodeRendererProps<any>) {
  const searchParams = useParams();
  const queryClient = useQueryClient();
  const router = useRouter();

  const slug = Array.isArray(searchParams.slug)
    ? searchParams.slug[0]
    : searchParams.slug;

  const deleteMutate = useMutation({
    mutationFn: deletePlot,
    onSuccess: () => {
      queryClient.refetchQueries(['plotDirectory']);
      queryClient.refetchQueries(['plotList']);
      if (searchParams.plot === node.id) {
        router.push(`/plot/${searchParams.slug}`);
      }
    },
  });

  return (
    <>
      <div
        className={`flex justify-between items-center text-violet-50 hover:text-black hover:bg-slate-50 ${
          node.isSelected ? 'bg-slate-50' : 'bg-violet-50'
        }`}
        onClick={() => {
          node.toggle();
        }}
        onDoubleClick={() => {
          if (node.isLeaf) {
            queryClient.removeQueries(['plot', node.id]);
            queryClient.refetchQueries(['plotList']);
            queryClient.refetchQueries(['plotDirectory']);
            router.push(`/plot/${slug}/${node.id}`);
          }
        }}
      >
        <div className="text-black">
          <div style={style} className="text-sm font-bold cursor-default">
            {node.isLeaf ? '📖' : node.isOpen ? '📂' : '📁'}
            {node.data.name}
          </div>
        </div>

        <div className={`file-actions flex items-center`}>
          <div className="folderFileActions flex items-center">
            <button
              onClick={() => {
                deleteMutate.mutate(node.id);
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
