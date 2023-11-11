import { Alarm } from '@/model/alarm';
import dayjs from 'dayjs';

export const getAlarmMessage = ({
  type,
  senderId,
  senderName,
  context,
  createdAt,
  nickname,
  produceName,
}: Alarm) => {
  const data = {
    message: '',
    link: '',
    time: 0,
  };

  data.time = getTime(createdAt);


  switch (type) {
    case 0:
      data.message = `${senderName} 농산물의 경매가 시작되었습니다.`;
      data.link = `/product/${senderId}`;
      break;
    case 1:
      data.message = `구독한 판매자 ${senderName}의 글이 등록되었습니다.`;
      data.link = `/otherpage/${senderId}`;
      break;
    case 2:
      data.message = `${nickname}님이 ${produceName}을 낙찰받으셨습니다.`;
      data.link = `/mypage`;
      break;
  }

  return data;
};

export const getTime = (createAt: Date) => {
  let today = dayjs();
  let expired_at = dayjs(createAt);
  let result = Math.floor(today.diff(expired_at, 'minute', true));
  return result;
};
