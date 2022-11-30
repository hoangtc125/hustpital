import dayjs from 'dayjs';

export interface IBhyt {
  id?: string;
  qrcode?: string | null;
  sothe?: string | null;
  maKCBBD?: string | null;
  diachi?: string | null;
  ngayBatDau?: string | null;
  ngayKetThuc?: string | null;
  ngayBatDau5namLT?: string | null;
  ngayBatDauMienCCT?: string | null;
  ngayKetThucMienCCT?: string | null;
}

export const defaultValue: Readonly<IBhyt> = {};
