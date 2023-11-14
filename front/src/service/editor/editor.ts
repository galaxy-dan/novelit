export const fontSize = ['lg', 'xl', '2xl', '3xl', '4xl'];
export const fontFamily = [
  'melody',
  'nanum',
  'noto',
  'nanumPen',
  'jalnan',
  'jalnanGothic',
];

export const getJwtPayload = (token: string) => {
  const base64Payload = token.split('.')[1];
  const payload = Buffer.from(base64Payload, 'base64');
  const result = JSON.parse(payload.toString());
  return result;
};

export async function wordCheck(req: { word: string }) {
  const response = await fetch('/bpi/word', {
    method: 'POST',
    body: JSON.stringify(req),
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
