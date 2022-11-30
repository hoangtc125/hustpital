import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';

export interface IPhongKham {
  id?: string;
  code?: string | null;
  name?: string | null;
  chuyenkhoa?: IChuyenKhoa;
}

export const defaultValue: Readonly<IPhongKham> = {};
