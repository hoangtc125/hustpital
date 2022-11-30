import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEthnics } from 'app/shared/model/ethnics.model';
import { ICountries } from 'app/shared/model/countries.model';
import { IBanks } from 'app/shared/model/banks.model';
import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';
import { ILichLamViec } from 'app/shared/model/lich-lam-viec.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IDoctors {
  id?: string;
  name?: string | null;
  phone?: string | null;
  citizenIdentification?: string | null;
  maBHXH?: string | null;
  gender?: Gender | null;
  dateOfBirth?: string | null;
  address?: string | null;
  maSoThue?: string | null;
  user?: IUser;
  ethnic?: IEthnics | null;
  country?: ICountries | null;
  bank?: IBanks | null;
  chuyenkhoa?: IChuyenKhoa | null;
  lichlamviecs?: ILichLamViec[] | null;
}

export const defaultValue: Readonly<IDoctors> = {};
