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
  { id: '1', name: '역사' },
  { id: '2', name: '에세이' },
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
  const [cursor, setCursor] = useState<any>(false);
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const treeRef = useRef<any>(null);
  const [term, setTerm] = useState<string>('');

  //   const onToggle = (node: any, toggled: any) => {
  //     if (cursor) {
  //       cursor.active = false;
  //     }
  //     node.active = true;

  //     if (node.children) {
  //       node.toggled = toggled;
  //     }

  //     setCursor(node);
  //     setData(Object.assign({}, data));
  //   };

  return (
    <>
      <button onClick={() => setIsOpen((prev) => !prev)}>열기</button>

      {isOpen && (
        <div className="fixed left-0 top-0 bg-violet-50 w-64 font-melody">
          <div>
            <div className="flex justify-between items-center p-4 border-b-2 border-gray-300">
              <div className="flex gap-2">
                <BiSolidHome size={30} />
                <div className="font-bold text-xl">소설 제목</div>
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
                <Tree
                  ref={treeRef}
                  initialData={temp2}
                  openByDefault={false}
                  width={200}
                  height={1000}
                  indent={24}
                  rowHeight={36}
                  paddingTop={30}
                  paddingBottom={10}
                  padding={25 /* sets both */}
                  searchTerm={term}
                  searchMatch={(node, term) =>
                    node.data.name.toLowerCase().includes(term.toLowerCase())
                  }
                >
                  {Node}
                </Tree>
                {/* <Treebeard data={data} onToggle={onToggle} /> */}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

function Node({ node, style, dragHandle, tree }: NodeRendererProps<any>) {
  /* This node instance can do many things. See the API reference. */
  return (
    <>
      <div
        className={`flex justify-between items-center text-violet-50 hover:text-black hover:bg-slate-50 ${
          node.isSelected ? 'bg-slate-50' : 'bg-violet-50'
        }`}
        ref={dragHandle}
        onClick={() => node.toggle()}
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
                if (e.key === 'Enter') node.submit(e.currentTarget.value);
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
            <button onClick={() => tree.delete(node.id)} title="Delete">
              <RxCross2 />
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
