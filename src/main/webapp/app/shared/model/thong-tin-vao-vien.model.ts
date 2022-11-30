import dayjs from 'dayjs';
import { IPatients } from 'app/shared/model/patients.model';
import { IPhongKham } from 'app/shared/model/phong-kham.model';

export interface IThongTinVaoVien {
  id?: string;
  ngayKham?: string | null;
  tinhTrangVaoVien?: string | null;
  soPhieu?: number | null;
  maBVChuyenDen?: number | null;
  benhChuyenDen?: string | null;
  patient?: IPatients;
  phongkham?: IPhongKham | null;
}

export const defaultValue: Readonly<IThongTinVaoVien> = {};
