import { IDoctors } from 'app/shared/model/doctors.model';

export interface ILichLamViec {
  id?: string;
  thu?: number | null;
  thoiGian?: string | null;
  doctors?: IDoctors[];
}

export const defaultValue: Readonly<ILichLamViec> = {};
