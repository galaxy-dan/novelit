'use client';

import { Reply } from '@/model/editor/editor';
import { fontSize } from '@/service/editor/editor';
import { ChangeEvent, useRef, useState } from 'react';

import ContentEditable, { ContentEditableEvent } from 'react-contenteditable';
import sanitizeHtml from 'sanitize-html';

import { BiSolidPencil, BiBold, BiSolidTrashAlt } from 'react-icons/bi';
import { PiTextTLight } from 'react-icons/pi';
import { FaCheck, FaShareSquare } from 'react-icons/fa';

export default function Editor() {
  const [html, setHtml] = useState<string>('<div><br/></div>');

  const [editable, setEditable] = useState<boolean>(true);
  const [fontIndex, setFontIndex] = useState<number>(2);

  const comment = useRef<Reply[]>([]);

  const handleChange = (e: ContentEditableEvent) => {
    setHtml(e.target.value);
  };

  const sanitizeConf = {
    allowedTags: ['b', 'i', 'em', 'strong', 'a', 'p', 'h1', 'div'],
    allowedAttributes: { a: ['href'] },
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

    const range = selection.getRangeAt(0);
    const wrapper = document.createElement("div");
    wrapper.id = "temp";
    // wrapper.onclick = '
    console.log("여기까지?");
    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);

    setHtml(document.getElementById("edit")?.innerHTML ?? html);

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

  return (
    <>
      <div className="flex justify-center w-screen text-4xl border-b-2 border-gray-100 pb-12 mb-6 mt-24">
        <div className='w-[924px]'>
            제목입니다.
        </div>
      </div>
      <div className="flex gap-6 justify-center items-center">
        <div className="flex flex-col justify-center items-center">
          <ContentEditable
            id="edit"
            className={`ml-2 w-[700px] min-h-screen p-1 resize-none text-${fontSize[fontIndex]} outline-none`}
            html={html}
            disabled={!editable}
            onChange={handleChange}
            onBlur={sanitize}
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
            className="p-4 bg-green-50 rounded-lg"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <BiSolidPencil size={20} />
          </button>
          <button
            className="p-4 bg-green-50 rounded-lg"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <PiTextTLight size={20} />
          </button>
          <button
            className="p-4 bg-green-50 rounded-lg"
            onClick={(e) => {
              clickExecCommand(e, 'bold');
            }}
          >
            <BiBold size={20} />
          </button>
          <button className="p-4 bg-green-50 rounded-lg" onClick={addReply}>
            <FaCheck size={20} />
          </button>
          <button
            className="p-4 bg-green-50 rounded-lg"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <BiSolidTrashAlt size={20} />
          </button>
          <button
            className="p-4 bg-green-50 rounded-lg"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <FaShareSquare size={20} />
          </button>
        </div>
      </div>
    </>
  );
}
