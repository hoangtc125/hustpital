import dayjs from 'dayjs';
import { IDoctors } from 'app/shared/model/doctors.model';
import { IUser } from 'app/shared/model/user.model';
import { Admission } from 'app/shared/model/enumerations/admission.model';

export interface ILichHen {
  id?: string;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  lyDoKham?: string | null;
  dateOfBirth?: string | null;
  lichhenType?: Admission | null;
  doctor?: IDoctors | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ILichHen> = {};
