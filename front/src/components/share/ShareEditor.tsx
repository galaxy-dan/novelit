'use client';

import { MouseEvent, RefObject } from 'react';

import { Editor, Reply } from '@/model/editor';
import { fontFamily, fontSize } from '@/service/editor/editor';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

import ContentEditable, { ContentEditableEvent } from 'react-contenteditable';
import sanitizeHtml, { IOptions } from 'sanitize-html';

import { BiSolidPencil, BiBold, BiSolidTrashAlt } from 'react-icons/bi';
import { PiTextTLight } from 'react-icons/pi';
import { FaCheck, FaShareSquare } from 'react-icons/fa';
import { useParams } from 'next/navigation';
import {
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { getComment, getEditor, patchEditor } from '@/service/api/editor';
import { toast } from 'react-toastify';
import Comment from '../editor/Comment';

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

  const edit = useRef<HTMLDivElement>(null);

  const { data: editor }: UseQueryResult<Editor> = useQuery({
    queryKey: ['editor', searchParams.slug],
    queryFn: () => getEditor({ uuid: searchParams.slug }),
    enabled: !!searchParams.slug,
  });

  // 자동 저장
  useEffect(() => {
    const time = setTimeout(() => {}, 2000);

    return () => clearTimeout(time);
  }, []);

  useEffect(() => {
    const editRef = edit?.current;
    if (editRef) {
      editRef.onclick = (e: any) => {
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

      editRef.onpaste = (e) => {
        const paste = e.clipboardData?.getData('text');

        if (!paste) return;
        const reversed = Array.from(paste).reverse().join('');

        const selection = window.getSelection();
        if (!selection?.rangeCount) return false;
        selection.deleteFromDocument();
        selection.getRangeAt(0).insertNode(document.createTextNode(paste));

        e.preventDefault();
      };
    }

    // edit?.current?.addEventListener('click', () => {
    //   toast('버블');
    // });
  }, []);

  useEffect(() => {
    // document.getElementById("editor")?.innerText =

    let content = editor?.content ?? '';
    content = content.length === 0 ? '<div><br/></div>' : content;
    setHtml(content);
  }, [editor?.content]);

  const patchMutate = useMutation({
    mutationFn: patchEditor,
    onSuccess: () => {
      queryClient.invalidateQueries(['editor']);
      toast('저장 성공');
    },
  });

  const handleChange = (e: ContentEditableEvent) => {
    setHtml(e.target.value);
    setLength(edit?.current?.innerText.length ?? 0);
  };

  const sanitizeConf: IOptions = {
    allowedTags: ['b', 'i', 'em', 'strong', 'a', 'p', 'h1', 'div', 'span'],
    // allowedAttributes: { a: ['href'] },
    allowedAttributes: false,
  };

  const sanitize = () => {
    setHtml((prev) => sanitizeHtml(prev, sanitizeConf));
  };

  const toggleEditable = () => {
    setEditable((prev) => !prev);
  };

  const addReply = () => {
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

    //임시
    // setSpaceUUID(wrapper.id);
    // 버블링 안되게
    // wrapper.onclick = () => {
    //   alert('되');
    //   setSpaceUUID('aa');
    // };
    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);

    setHtml(edit?.current?.innerHTML ?? html);
    setSpaceUUID(wrapper.id);
    setIsOpen(true);
  };

  const addReply2 = () => {
    // const selection = window.getSelection();
    // console.log(selection?.rangeCount);
    // console.log(selection?.isCollapsed);
    // // 선택한 부분이 없으면 return
    // if (selection?.isCollapsed) return;
    // if (!selection?.rangeCount) return;
  };

  const clickExecCommand = (
    e: React.MouseEvent<HTMLButtonElement>,
    cmd: string,
  ) => {
    e.preventDefault();
    document.execCommand(cmd, false);
    // document.execCommand(props.cmd, false, props.arg);
    0;
  };

  const shareDoc = () => {
    const divContent = edit?.current?.innerText;
    if (!divContent) return;

    var blob = new Blob([divContent], { type: 'text/plain;charset=utf-8' });

    var a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'output.doc';

    a.click();
  };

  return (
    <>
      <div
        className={`flex justify-center w-screen text-4xl border-b-2 border-gray-100 pb-12 mb-6 mt-24 font-${fontFamily[fontFamilyIndex]}`}
      >
        <div className="w-[924px]">{editor?.title}</div>
        <button
          onClick={() => {
            patchMutate.mutate({
              uuid: searchParams.slug,
              content: edit?.current?.innerHTML ?? '<div><br/></div>',
            });
          }}
        >
          임시저장
        </button>
      </div>
      <div className="flex gap-6 justify-center items-center">
        <div className="flex flex-col justify-center items-center">
          <ContentEditable
            innerRef={edit}
            id="edit"
            className={`ml-2 w-[700px] min-h-screen p-1 resize-none text-${fontSize[fontIndex]} outline-none font-${fontFamily[fontFamilyIndex]}`}
            html={html}
            disabled={true}
            onChange={handleChange}
            // onBlur={sanitize}
          />
          <h3>source</h3>
          <textarea
            className="ml-2 w-3/4 min-h-[100px] border-gray-100 border-2 p-1 resize-none"
            value={html}
            onChange={handleChange}
            onBlur={sanitize}
          />
        </div>

        <div className="flex flex-col w-[200px] justify-start items-center gap-6">
          <button
            className="p-4 bg-green-50 bg-opacity-40 rounded-lg"
            onClick={addReply}
          >
            <FaCheck size={20} />
          </button>
          
          <button
            className="p-4 bg-green-50 bg-opacity-40 rounded-lg"
            onClick={shareDoc}
          >
            <FaShareSquare size={20} />
          </button>
          {isOpen && (
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