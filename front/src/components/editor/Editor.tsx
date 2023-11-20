'use client';

import { MouseEvent, RefObject } from 'react';

import { Editor, Reply, Word } from '@/model/editor';
import { fontFamily, fontSize, wordCheck } from '@/service/editor/editor';
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
  BiSolidDownload,
} from 'react-icons/bi';
import { PiTextTLight } from 'react-icons/pi';
import { FaCheck, FaShareSquare } from 'react-icons/fa';
import { FaBook } from 'react-icons/fa';

import { MdEdit, MdEditOff } from 'react-icons/md';
import { GiToken } from 'react-icons/gi';
import { AiOutlineFileSearch, AiFillCaretDown } from 'react-icons/ai';
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
import Comment from './Comment';
import { get } from '@/service/api/http';
import UploadState from '../state/UploadState';
import { getShareToken } from '@/service/api/share';
import WordModal from './WordModal';
import WordBookModal from './WordBookModal';

export default function Editor() {
  const searchParams = useParams();
  const queryClient = useQueryClient();

  const [html, setHtml] = useState<string>('');
  const [length, setLength] = useState<number>();

  const [editable, setEditable] = useState<boolean>(true);
  const [fontIndex, setFontIndex] = useState<number>(2);
  const [fontFamilyIndex, setFontFamilyIndex] = useState<number>(0);

  const [spaceUUID, setSpaceUUID] = useState<string>('');
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const comment = useRef<Reply[]>([]);

  const [uploadIndex, setUploadIndex] = useState<number>(0);

  const [wordList, setWordList] = useState<Word[]>([]);
  const [isOpenWord, setIsOpenWord] = useState<boolean>(false);

  const [isOpenWordbook, setIsOpenWordbook] = useState<boolean>(false);

  const edit = useRef<HTMLDivElement>(null);

  const { data: editor }: UseQueryResult<Editor> = useQuery({
    queryKey: ['editor', searchParams.slug?.[1]],
    queryFn: () => getEditor({ uuid: searchParams.slug?.[1] }),
    enabled: !!searchParams.slug?.[1],
  });

  const [throttle, setThrottle] = useState<boolean>(false);
  // 자동 저장 - throttle
  useEffect(() => {
    if (editor?.content === html) return;

    if (throttle) return;
    if (!throttle) {
      setThrottle(true);
      setTimeout(() => {
        if (!edit.current?.innerHTML) return;

        patchMutate.mutate({
          uuid: searchParams.slug?.[1],
          content: edit.current?.innerHTML,
        });
        setThrottle(false);
      }, 5000);
    }
  }, [html]);

  // debounce
  // useEffect(() => {
  //   if (editor?.content === html) return;
  //   const time = setTimeout(() => {
  //     patchMutate.mutate({
  //       uuid: searchParams.slug?.[1],
  //       content: html ?? '',
  //     });
  //   }, 2000);

  //   return () => clearTimeout(time);
  // }, [html]);

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

          setIsOpen(true);
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
    let content = editor?.content ?? '';
    content = content.length === 0 ? '' : content;
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
      // toast('저장 성공');
    },
  });

  const handleChange = (e: ContentEditableEvent) => {
    setUploadIndex(1);
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

  const addReply = () => {
    if (editor?.editable) {
      toast('글 작성중이어서 댓글을 작성할 수 없습니다.');
      return;
    }
    const selection = window.getSelection();

    // 선택한 부분이 없으면 return
    if (selection?.isCollapsed) return;

    if (!selection?.rangeCount) return;

    if (selection.focusNode?.parentNode?.nodeName !== 'SPAN') return;

    const range = selection.getRangeAt(0);
    const wrapper = document.createElement('span');
    wrapper.id = uuidv4();

    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);

    patchMutate.mutate({
      uuid: searchParams.slug?.[1],
      content: edit.current?.innerHTML ?? '',
    });

    setHtml(edit?.current?.innerHTML ?? html);
    setSpaceUUID(wrapper.id);
    setIsOpen(true);
  };

  const addReply2 = () => {
    // const selection = window.getSelection();
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
    a.download = `${editor?.title}.txt`;

    a.click();
  };

  const getMutation = useMutation({
    mutationFn: getShareToken,
    onSuccess: (data: any) => {
      navigator.clipboard
        .writeText(data.token)
        .then(() => toast('토큰이 복사되었습니다.'));

      editableMutate.mutate({
        directoryUUID: searchParams.slug?.[1],
        editable: false,
      });
      // toast(data.token, {
      //   autoClose: false,
      //   onClose: () =>

      // });

      // localStorage.setItem('accessToken', data.token);
    },
  });

  const getToken = () => {
    // get('/share/token', { directoryUUID: searchParams.slug?.[1] }).then(
    //   (data: any) => {

    //     toast(data.token);
    //     localStorage.setItem('accessToken', data.token);
    //   },
    // );
    getMutation.mutate({ directoryUUID: searchParams.slug?.[1] });
  };

  const editableMutate = useMutation({
    mutationFn: patchEditable,
    onSuccess: () => {
      // toast('토글 성공');
      setIsOpen(false);
      queryClient.invalidateQueries(['editor', searchParams.slug?.[1]]);
    },
  });

  const toggleEditable = () => {
    editableMutate.mutate({
      directoryUUID: searchParams.slug?.[1],
      editable: !editor?.editable,
    });
  };

  return (
    <>
      <div
        className={`flex justify-center text-4xl border-b-2 border-gray-100 pb-12 mb-6 mt-24 font-${fontFamily[fontFamilyIndex]}`}
      >
        <div className="flex justify-between w-[1160px]">
          <div>{editor?.title}</div>
          <UploadState state={uploadIndex} />
        </div>
        {/* <button
          onClick={() => {
            patchMutate.mutate({
              uuid: searchParams.slug?.[1],
              content: edit?.current?.innerHTML ?? '',
            });
          }}
        >
          임시저장
        </button> */}
      </div>
      <div className="flex gap-6 justify-center items-start">
        <div className=" flex flex-col justify-center items-center">
          <ContentEditable
            innerRef={edit}
            className={`ml-2 w-[960px] min-h-screen p-1 resize-none text-${fontSize[fontIndex]} outline-none font-${fontFamily[fontFamilyIndex]} break-words`}
            html={html}
            disabled={!editor?.editable ?? false}
            onChange={handleChange}
            tagName="span"
            // onBlur={sanitize}
          />
          {/* <h3>source</h3>
          <textarea
            className="ml-2 w-3/4 min-h-[100px] border-gray-100 border-2 p-1 resize-none"
            value={html}
            onChange={handleChange}
            onBlur={sanitize}
          /> */}
        </div>

        <div className="flex flex-col w-[180px] justify-start items-center gap-6">
          {/* <div>{document && document?.getElementById('edit')?.innerText.length && 0}</div> */}
          <button
            onClick={() => {
              setIsOpenWord(true);
            }}
          >
            <AiFillCaretDown size={20} />
          </button>
          <div className="text-2xl">{length && `${length}자`}</div>

          <button className="flex items-center" onClick={toggleEditable}>
            <div className="p-4 rounded-lg">
              {editor?.editable ? (
                <MdEdit size={20} />
              ) : (
                <MdEditOff size={20} />
              )}
            </div>
            <div className="text-xs font-bold whitespace-nowrap">쓰기</div>
          </button>

          <button
            className="flex items-center"
            onClick={() => {
              setFontFamilyIndex((prev) => (prev + 1) % fontFamily.length);
            }}
          >
            <div className="p-4 rounded-lg">
              <BiFontFamily size={20} />
            </div>
            <div className="text-xs font-bold">글꼴</div>
          </button>

          <button
            className="flex items-center"
            onClick={() => {
              setFontIndex((prev) => (prev + 1) % fontSize.length);
            }}
          >
            <div className="p-4 rounded-lg">
              <BiFontSize size={20} />
            </div>
            <div className="text-xs font-bold">크기</div>
          </button>

          <button
            className="flex items-center"
            onClick={(e) => {
              clickExecCommand(e, 'bold');
            }}
          >
            <div className="p-4 rounded-lg">
              <BiBold size={20} />
            </div>
            <div className="text-xs font-bold">굵기</div>
          </button>

          <button className="flex items-center" onClick={addReply}>
            <div className="p-4 rounded-lg">
              <BiCommentDetail size={20} />
            </div>
            <div className="text-xs font-bold">댓글</div>
          </button>

          <button className="flex items-center" onClick={shareDoc}>
            <div className="p-4 rounded-lg">
              <BiSolidDownload size={20} />
            </div>
            <div className="text-xs font-bold">다운</div>
          </button>
          <button className="flex items-center" onClick={getToken}>
            <div className="p-4 rounded-lg">
              <GiToken size={20} />
            </div>
            <div className="text-xs font-bold">토큰</div>
          </button>
          <button
            className="flex items-center"
            onClick={() => setIsOpenWordbook(true)}
          >
            <div className="p-4 rounded-lg">
              <FaBook size={20} />
            </div>
            <div className="text-xs font-bold">단어</div>
          </button>
          <button
            className="flex items-center"
            onClick={() =>
              wordCheck({
                word: edit.current?.innerText ?? '',
                workspaceUUID: searchParams.slug?.[0],
              }).then((data) => {
                setWordList(data);
                setIsOpenWord(true);
              })
            }
          >
            <div className="p-4 rounded-lg">
              <AiOutlineFileSearch size={20} />
            </div>
            <div className="text-xs font-bold">검사</div>
          </button>
          {isOpen && (
            <Comment
              spaceUUID={spaceUUID}
              directoryUUID={searchParams.slug?.[1]}
              setIsOpen={setIsOpen}
              setHtml={setHtml}
              editRef={edit}
            />
          )}
        </div>
      </div>

      {isOpenWord && (
        <WordModal
          wordList={wordList}
          setIsOpenWord={setIsOpenWord}
          word={edit.current?.innerText ?? ''}
          workspaceUUID={searchParams.slug?.[0]}
          setWordList={setWordList}
        />
      )}

      {isOpenWordbook && (
        <WordBookModal
          workspaceUUID={searchParams.slug?.[0]}
          setIsOpenWordbook={setIsOpenWordbook}
        />
      )}
    </>
  );
}
