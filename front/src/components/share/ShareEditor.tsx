'use client';

import { MouseEvent, RefObject } from 'react';

import { Editor, Reply } from '@/model/editor';
import { fontFamily, fontSize } from '@/service/editor/editor';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

import ContentEditable, { ContentEditableEvent } from 'react-contenteditable';
import sanitizeHtml, { IOptions } from 'sanitize-html';

import {
  BiSolidPencil,
  BiBold,
  BiSolidTrashAlt,
  BiCommentDetail,
  BiFontSize,
  BiFontFamily,
} from 'react-icons/bi';
import { PiTextTLight } from 'react-icons/pi';
import { FaCheck, FaShareSquare } from 'react-icons/fa';
import { MdEdit, MdEditOff } from 'react-icons/md';
import { useParams } from 'next/navigation';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import {
  getComment,
  getEditor,
  patchEditable,
  patchEditor,
} from '@/service/api/editor';
import { toast } from 'react-toastify';
import Comment from '../editor/Comment';
import { get } from '@/service/api/http';
import UploadState from '../state/UploadState';

export default function ShareEditor() {
  const searchParams = useParams();
  const queryClient = useQueryClient();

  const [html, setHtml] = useState<string>('<div><br/></div>');
  const [length, setLength] = useState<number>();

  const [editable, setEditable] = useState<boolean>(true);
  const [fontIndex, setFontIndex] = useState<number>(2);
  const [fontFamilyIndex, setFontFamilyIndex] = useState<number>(0);

  const [spaceUUID, setSpaceUUID] = useState<string>('');
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const comment = useRef<Reply[]>([]);

  const [uploadIndex, setUploadIndex] = useState<number>(0);

  const edit = useRef<HTMLDivElement>(null);

  const { data: editor }: UseQueryResult<Editor> = useQuery({
    queryKey: ['editor', searchParams.slug],
    queryFn: () => getEditor({ uuid: searchParams.slug }),
    enabled: !!searchParams.slug,
  });



  useEffect(() => {
    const editRef = edit?.current;
    if (editRef) {
      editRef.onclick = (e: any) => {
        // 댓글
        const id = e.target.id;
        if (!id || id === 'edit') {
          setSpaceUUID('');
          setIsOpen(false);
        } else {
          // const id = e.target.id;
          setSpaceUUID(id);
          // console.log(e.target.id);
          setIsOpen(true);
          toast('버블');
        }
      };
    }
  }, []);

  useEffect(() => {
    // document.getElementById("editor")?.innerText =

    let content = editor?.content ?? '';
    content = content.length === 0 ? '<div><br/></div>' : content;
    setHtml(content);
  }, [editor?.content]);

  const patchMutate = useMutation({
    mutationFn: patchEditor,
    onMutate: () => {
      setUploadIndex(2);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['editor']);
      setUploadIndex(3);
      toast('저장 성공');
    },
  });

  const sanitizeConf: IOptions = {
    allowedTags: ['b', 'i', 'em', 'strong', 'a', 'p', 'h1', 'div', 'span'],
    // allowedAttributes: { a: ['href'] },
    allowedAttributes: false,
  };

  const sanitize = () => {
    setHtml((prev) => sanitizeHtml(prev, sanitizeConf));
  };

  const addReply = () => {
    if (editor?.editable) {
      toast("글 작성중이어서 댓글을 작성할 수 없습니다.")
      return;
    }

    const selection = window.getSelection();
    console.log(selection?.rangeCount);
    console.log(selection?.isCollapsed);

    // 선택한 부분이 없으면 return
    if (selection?.isCollapsed) return;

    if (!selection?.rangeCount) return;

    console.log('여기까지?');
    const range = selection.getRangeAt(0);
    const wrapper = document.createElement('span');
    wrapper.id = uuidv4();

    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);

    setHtml(edit?.current?.innerHTML ?? html);
    setSpaceUUID(wrapper.id);
    setIsOpen(true);
  };

  

  return (
    <>
    <div>편집자 페이지</div>
      <div
        className={`flex justify-center w-screen text-4xl border-b-2 border-gray-100 pb-12 mb-6 mt-24 font-${fontFamily[fontFamilyIndex]}`}
      >
        <div className="flex justify-between w-[1160px]">
          <div>{editor?.title}</div>
        </div>
      </div>
      <div className="flex gap-6 justify-center items-start">
        <div className=" flex flex-col justify-center items-center">
          <ContentEditable
            innerRef={edit}
            id="edit"
            className={`ml-2 w-[960px] min-h-screen p-1 resize-none text-${fontSize[fontIndex]} outline-none font-${fontFamily[fontFamilyIndex]}`}
            html={html}
            disabled={true}
            onChange={() => {}}
            // onBlur={sanitize}
          />
        </div>

        <div className="flex flex-col w-[200px] justify-start items-center gap-6">
          <button
            className="p-4 bg-green-50 bg-opacity-40 rounded-lg"
            onClick={addReply}
          >
            <BiCommentDetail size={20} />
          </button>

          {isOpen && !editor?.editable && (
            <Comment
              spaceUUID={spaceUUID}
              directoryUUID={searchParams.slug}
              setIsOpen={setIsOpen}
            />
          )}
        </div>
      </div>
    </>
  );
}
