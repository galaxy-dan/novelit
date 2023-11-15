// import { sendEmail } from "@/service/email";
import * as yup from 'yup';
import hanspell from 'hanspell';
import { get } from '@/service/api/http';
import axios from 'axios';
import { WordInfo } from '@/model/wordbook';

const bodySchema = yup.object().shape({
  from: yup.string().email().required(),
  subject: yup.string().required(),
  message: yup.string().required(),
});

export async function POST(req: Request) {
  const { word, workspaceUUID } = await req.json();

  // console.log(req);
  // const data = await req.json();

  // const word = data.word;

  // console.log(req.headers.get('Authorization'));

  // const wordList = await get('/workspace/words', { workspaceUUID });

  const wordbook = await axios.get(
    `${process.env.NEXT_PUBLIC_BACKEND_URL}/words`,
    {
      headers: {
        Authorization: req.headers.get('Authorization'),
        'Content-Type': 'application/json',
      },
      params: {
        workspaceUUID,
      },
    },
  );

  // const wordCheck: any = {};
  // wordbook.data.wordInfo.map((el: WordInfo) => {
  //   wordCheck[el.word] = true;
  // });

  // console.log(wordCheck);

  // console.log('local', localStorage.getItem('accessToken'));
  const end = function () {};

  const getHanspell = (word: any) => {
    return new Promise((resolve, reject) => {
      const end = (err: any, res: any) => {
        if (err) {
          reject(err);
        } else {
          resolve(res);
        }
      };
      const error = (err: any) => {
        reject(err);
      };

      hanspell.spellCheckByPNU(word, 6000, resolve, end, error);
    });
  };

  const getResult = async () => {
    try {
      const data: any = await getHanspell(word);
      const result = [];

      outer: for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < wordbook.data.wordInfo.length; j++) {
          if (data[i].token.includes(wordbook.data.wordInfo[j].word)) {
            continue outer;
          }
        }
        result.push(data[i]);
      }

      return new Response(JSON.stringify(result), {
        status: 200,
      });
    } catch (error) {
      console.error(error);
    }
  };

  return getResult();
}
