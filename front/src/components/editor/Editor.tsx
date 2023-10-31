'use client';

import { Reply } from '@/model/editor';
import { fontFamily, fontSize } from '@/service/editor/editor';
import { ChangeEvent, useRef, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

import ContentEditable, { ContentEditableEvent } from 'react-contenteditable';
import sanitizeHtml, { IOptions } from 'sanitize-html';

import { BiSolidPencil, BiBold, BiSolidTrashAlt } from 'react-icons/bi';
import { PiTextTLight } from 'react-icons/pi';
import { FaCheck, FaShareSquare } from 'react-icons/fa';

export default function Editor() {
  const [html, setHtml] = useState<string>('<div><br/></div>');

  const [editable, setEditable] = useState<boolean>(true);
  const [fontIndex, setFontIndex] = useState<number>(2);
  const [fontFamilyIndex, setFontFamilyIndex] = useState<number>(0);

  const comment = useRef<Reply[]>([]);

  const handleChange = (e: ContentEditableEvent) => {
    setHtml(e.target.value);
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

    // 버블링 안되게
    // wrapper.onclick = '
    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);

    setHtml(document.getElementById('edit')?.innerHTML ?? html);
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
    const divContent = document.getElementById('edit')?.innerText;
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
        <div className="w-[924px]">제목입니다.</div>
      </div>
      <div className="flex gap-6 justify-center items-center">
        <div className="flex flex-col justify-center items-center">
          <ContentEditable
            id="edit"
            className={`ml-2 w-[700px] min-h-screen p-1 resize-none text-${fontSize[fontIndex]} outline-none font-${fontFamily[fontFamilyIndex]}`}
            html={html}
            disabled={!editable}
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
            onClick={() => {
              setFontFamilyIndex((prev) => (prev + 1) % fontFamily.length);
            }}
          >
            <BiSolidPencil size={20} />
          </button>
          <button
            className="p-4 bg-green-50 bg-opacity-40 rounded-lg"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <PiTextTLight size={20} />
          </button>
          <button
            className="p-4 bg-green-50 bg-opacity-40 rounded-lg"
            onClick={(e) => {
              clickExecCommand(e, 'bold');
            }}
          >
            <BiBold size={20} />
          </button>
          <button className="p-4 bg-green-50 bg-opacity-40 rounded-lg" onClick={addReply}>
            <FaCheck size={20} />
          </button>
          <button className="p-4 bg-green-50 bg-opacity-40 rounded-lg" onClick={addReply2}>
            <BiSolidTrashAlt size={20} />
          </button>
          <button className="p-4 bg-green-50 bg-opacity-40 rounded-lg" onClick={shareDoc}>
            <FaShareSquare size={20} />
          </button>
        </div>
      </div>
    </>
  );
}
