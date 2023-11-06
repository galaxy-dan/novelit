'use client';

import React, { useEffect, useRef, useState } from 'react';
import { FaRegTrashAlt, FaMinus } from 'react-icons/fa';
import { HiPlus } from 'react-icons/hi';
import Image from 'next/image';
import { getS3URL, uploadImage } from '@/service/image/image';
import {
  characterType,
  informationType,
  relationshipType,
} from '@/model/charactor';
import UploadState from '@/components/state/UploadState';
import styles from '@/service/cssStyle/scrollbar.module.css';
import {
  deleteCharacter,
  getCharacter,
  putCharacter,
} from '@/service/api/character';
import {
  UseMutationResult,
  UseQueryResult,
  useMutation,
  useQuery,
} from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { plotType } from '@/model/plot';
import { getPlot, putPlot } from '@/service/api/plot';

type Props = {
  params: {
    workspace: string;
    plot: string;
  };
};

export default function page({ params }: Props) {
  const router = useRouter();

  const [isFetched, setIsFetched] = useState<boolean>(false);

  const { data: plotData }: UseQueryResult<plotType> = useQuery({
    queryKey: ['plot', params.plot],
    queryFn: () => getPlot(params.plot),
    onSuccess: (data) => {
      setPlot((prev) => ({ ...prev, plotUuid: params.plot }));
      setPlotTitleInput(data?.plotTitle || '');
      setStoryInput(data?.story || '');
      setBeginningInput(data?.beginning || '');
      setRisingInput(data?.rising || '');
      setCrisisInput(data?.crisis || '');
      setClimaxInput(data?.climax || '');
      setEndingInput(data?.ending || '');
    },
    onError: () => {
      router.push(`/plot/${params.workspace}`);
    },
    enabled: !isFetched,
  });

  const putCharacterMutation = useMutation({
    mutationFn: () => putPlot(params.plot, plot),
    onSuccess: () => {
      setState(3);
    },
    onError: () => {
      setState(4);
    },
    onMutate: () => {
      setState(2);
    },
  });

  const deleteCharacterMutation = useMutation({
    mutationFn: () => deleteCharacter(params.plot),
    onSuccess: () => {
      router.push(`/plot/${params.workspace}`);
    },
    onError: () => {
      setState(4);
    },
  });

  const [plot, setPlot] = useState<plotType>({
    plotTitle: '',
    plotUuid: '',
    story: '',
    beginning: '',
    rising: '',
    crisis: '',
    climax: '',
    ending: '',
  });

  const ref = useRef<characterType | null>(null);

  const [plotTitleInput, setPlotTitleInput] = useState<string | null>('');
  const [storyInput, setStoryInput] = useState<string | null>('');

  const [width, setWidth] = useState(100);
  const nameRef = useRef<HTMLInputElement>(null);

  const [beginningInput, setBeginningInput] = useState<string | null>('');
  const [risingInput, setRisingInput] = useState<string | null>('');
  const [crisisInput, setCrisisInput] = useState<string | null>('');
  const [climaxInput, setClimaxInput] = useState<string | null>('');
  const [endingInput, setEndingInput] = useState<string | null>('');

  const beginningRef = useRef<HTMLTextAreaElement>(null);
  const risingRef = useRef<HTMLTextAreaElement>(null);
  const crisisRef = useRef<HTMLTextAreaElement>(null);
  const climaxRef = useRef<HTMLTextAreaElement>(null);
  const endingRef = useRef<HTMLTextAreaElement>(null);

  const [state, setState] = useState<number>(0);

  const hello = () => {
    setPlot((prev) => ({
      ...prev,
      plotTitle: plotTitleInput,
      story: storyInput,
      beginning: beginningInput,
      rising: risingInput,
      crisis: crisisInput,
      climax: climaxInput,
      ending: endingInput,
    }));
  };

  useEffect(() => {
    const debounce = setTimeout(() => {
      return hello();
    }, 1300);
    return () => {
      clearTimeout(debounce);
    };
  }, [
    plotTitleInput,
    storyInput,
    beginningInput,
    risingInput,
    crisisInput,
    climaxInput,
    endingInput,
  ]);

  useEffect(() => {
    if (ref.current && JSON.stringify(ref.current) !== JSON.stringify(plot)) {
      if (isFetched) {
        putCharacterMutation.mutate();
      } else {
        setIsFetched(true);
      }
    }
    ref.current = JSON.parse(JSON.stringify(plot));
  }, [plot]);

  useEffect(() => {
    if (nameRef !== null && nameRef.current !== null) {
      if (nameRef.current.offsetWidth > 100) {
        setWidth(nameRef.current.offsetWidth + 5);
      } else {
        setWidth(100);
      }
    }
  }, [plotTitleInput]);

  const [windowSize, setWindowSize] = useState<number>(0);
  useEffect(() => {
    function handleResize() {
      setWindowSize(window.innerWidth);
    }
    window.addEventListener('resize', handleResize);
    handleResize();
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  useEffect(() => {
    if (beginningRef.current) {
      beginningRef.current.style.height = 'auto';
      beginningRef.current.style.height =
        beginningRef.current.scrollHeight + 'px';
    }
  }, [beginningInput, windowSize]);

  useEffect(() => {
    if (risingRef.current) {
      risingRef.current.style.height = 'auto';
      risingRef.current.style.height = risingRef.current.scrollHeight + 'px';
    }
  }, [risingInput, windowSize]);

  useEffect(() => {
    if (crisisRef.current) {
      crisisRef.current.style.height = 'auto';
      crisisRef.current.style.height = crisisRef.current.scrollHeight + 'px';
    }
  }, [crisisInput, windowSize]);

  useEffect(() => {
    if (climaxRef.current) {
      climaxRef.current.style.height = 'auto';
      climaxRef.current.style.height = climaxRef.current.scrollHeight + 'px';
    }
  }, [climaxInput, windowSize]);

  useEffect(() => {
    if (endingRef.current) {
      endingRef.current.style.height = 'auto';
      endingRef.current.style.height = endingRef.current.scrollHeight + 'px';
    }
  }, [endingInput, windowSize]);

  if (typeof window !== 'undefined') {
    window.onbeforeunload = function (e) {
      // 입력 중이거나 저장 중일때는 나갈지 묻는다.
      if (state !== 1 && state !== 2) {
        return;
      }
      //메시지는 사용할 수 없다. 커스텀 메세지를 막아놓음..
      var dialogText =
        '아직 저장이 완료되지 않았습니다. 페이지를 정말로 이동하시겠습니까?';
      e.returnValue = dialogText;
      return dialogText;
    };
  }

  return (
    <div className="ml-32 my-20 w-[60vw] min-w-[50rem] max-w-[100rem] select-none">
      {/* 상단 타이틀 메뉴 + 로딩 상태 */}
      <div className="flex items-end justify-between">
        <div className="flex items-center">
          <div>
            <span
              ref={nameRef}
              className="invisible opacity-0 absolute text-4xl font-extrabold"
            >
              {plotTitleInput}
            </span>
            <input
              className="text-4xl font-extrabold max-w-[30rem] truncate"
              style={{ width }}
              type="text"
              onChange={(e) => {
                setPlotTitleInput(e.target.value);
                setState(1);
              }}
              value={plotTitleInput || ''}
            />
          </div>

          <FaRegTrashAlt
            className="text-2xl mb-1 cursor-pointer"
            onClick={() => {
              deleteCharacterMutation.mutate();
            }}
          />
        </div>

        <UploadState state={state} />
      </div>
      {/* 캐릭터 이미지 및 설명 */}
      <div className="flex h-64 mt-6">
        <div className="flex flex-col flex-grow justify-between">
          <p className="text-xl font-bold">캐릭터 설명</p>
          <div className="border-2 border-gray-300 rounded-xl h-56 p-2">
            <textarea
              className={`${styles.scroll} resize-none outline-none font-bold text-lg w-full h-full`}
              value={storyInput || ''}
              onChange={(e) => {
                if (e.target.value.length < 1000) {
                  setStoryInput(e.target.value);
                  setState(1);
                } else {
                  alert('글자 수 제한 1000자!');
                }
              }}
            />
          </div>
        </div>
      </div>

      {/* 기본 정보 */}
      <div className="mt-8">
        <p className="text-xl font-extrabold">기본 정보</p>
        <table className="text-xl border w-full border-gray-300 rounded-xl border-separate border-spacing-0">
          <tbody>
            <tr className="relative" key="발단">
              <td className="rounded-tl-xl rounded-bl-xl border border-gray-300 w-1/5 px-2 py-1 text-center">
                <p className="w-full my-auto text-center font-bold">발단</p>
              </td>
              <td className="rounded-tr-xl rounded-br-xl border border-gray-300 w-4/5 px-2">
                <textarea
                  className="w-full resize-none outline-none text-center font-bold"
                  value={beginningInput || ''}
                  ref={beginningRef}
                  rows={1}
                  onChange={(e) => {
                    setBeginningInput(e.target.value);
                  }}
                ></textarea>
              </td>
            </tr>

            <tr className="relative" key="전개">
              <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                <p className="w-full my-auto text-center font-bold">전개</p>
              </td>
              <td className="border border-gray-300 w-4/5 px-2">
                <textarea
                  className="w-full resize-none outline-none text-center font-bold"
                  value={risingInput || ''}
                  ref={risingRef}
                  rows={1}
                  onChange={(e) => {
                    setRisingInput(e.target.value);
                  }}
                ></textarea>
              </td>
            </tr>

            <tr className="relative" key="위기">
              <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                <p className="w-full my-auto text-center font-bold">위기</p>
              </td>
              <td className="border border-gray-300 w-4/5 px-2">
                <textarea
                  className="w-full resize-none outline-none text-center font-bold"
                  value={crisisInput || ''}
                  ref={crisisRef}
                  rows={1}
                  onChange={(e) => {
                    setCrisisInput(e.target.value);
                  }}
                ></textarea>
              </td>
            </tr>

            <tr className="relative" key="절정">
              <td className="border border-gray-300 w-1/5 px-2 py-1 text-center">
                <p className="w-full my-auto text-center font-bold">절정</p>
              </td>
              <td className="border border-gray-300 w-4/5 px-2">
                <textarea
                  className="w-full resize-none outline-none text-center font-bold"
                  value={climaxInput || ''}
                  ref={climaxRef}
                  rows={1}
                  onChange={(e) => {
                    setClimaxInput(e.target.value);
                  }}
                ></textarea>
              </td>
            </tr>

            <tr className="relative" key="결말">
              <td className="rounded-bl-xl border border-gray-300 w-1/5 px-2 py-1 text-center">
                <p className="w-full my-auto text-center font-bold">결말</p>
              </td>
              <td className="rounded-br-xl border border-gray-300 w-4/5 px-2">
                <textarea
                  className="w-full resize-none outline-none text-center font-bold"
                  value={endingInput || ''}
                  ref={endingRef}
                  rows={1}
                  onChange={(e) => {
                    setEndingInput(e.target.value);
                  }}
                ></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}
