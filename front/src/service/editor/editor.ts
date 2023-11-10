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
