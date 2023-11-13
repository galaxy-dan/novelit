import hanspell from 'hanspell';

export default function Word() {
  async function sendContactEmail(email: any) {
    const response = await fetch('/api/word', {
      method: 'POST',
      body: JSON.stringify(email),
      headers: {
        'Content-Type': 'application/json',
      },
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || '서버 요청에 실패함');
    }
    return data;
  }
  return (
    <button
      onClick={() => {
        sendContactEmail({ word: '우리의 나라는 좋슾니다.' }).then((data) => {
          console.log(data);
        });
        // const sentence = '리랜드는 얼굴 골격이 굵은게,';
        // const end = function () {
        //   console.log('// check ends');
        // };
        // const error = function (err: any) {
        //   console.error('// error: ' + err);
        // };

        // const getData = (data: any) => {
        //   console.log(data);
        // };

        // hanspell.spellCheckByPNU(sentence, 6000, getData, end, error);
      }}
    >
      맞춤법
    </button>
  );
}
