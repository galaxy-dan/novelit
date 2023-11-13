// import { sendEmail } from "@/service/email";
import * as yup from 'yup';
import hanspell from 'hanspell';

const bodySchema = yup.object().shape({
  from: yup.string().email().required(),
  subject: yup.string().required(),
  message: yup.string().required(),
});

export async function POST(req: Request) {
  const {word} = await req.json();

  //   if (!bodySchema.isValidSync(body)) {
  //     return new Response(JSON.stringify({ message: "메일 전송에 실패함!" }), {
  //       status: 400,
  //     });
  //   }

  //   const { from, subject, message } = body;

//   const sentence = '리랜드는 얼굴 골격이 rnf은게,';
  const end = function () {
    // console.log('// check ends');
  };
//   const error = function (err: any) {
//     console.error('// error: ' + err);
//   };


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

  const process = async () => {
    try {
      const data = await getHanspell(word);
      return new Response(JSON.stringify(data), {
        status: 200,
      });
    } catch (error) {
      console.error(error);
    }
  };

  return process();
}
