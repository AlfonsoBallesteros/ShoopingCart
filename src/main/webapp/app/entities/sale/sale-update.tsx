import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './sale.reducer';
import { ISale } from 'app/shared/model/sale.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISaleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SaleUpdate = (props: ISaleUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { saleEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/sale' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...saleEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="shoppingCartApp.sale.home.createOrEditLabel">Create or edit a Sale</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : saleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="sale-id">ID</Label>
                  <AvInput id="sale-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="sale-code">
                  Code
                </Label>
                <AvField id="sale-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="sale-date">
                  Date
                </Label>
                <AvInput
                  id="sale-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.saleEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="medioPagoLabel" for="sale-medioPago">
                  Medio Pago
                </Label>
                <AvField id="sale-medioPago" type="text" name="medioPago" />
              </AvGroup>
              <AvGroup>
                <Label id="subTotalLabel" for="sale-subTotal">
                  Sub Total
                </Label>
                <AvField id="sale-subTotal" type="string" className="form-control" name="subTotal" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLabel" for="sale-total">
                  Total
                </Label>
                <AvField id="sale-total" type="string" className="form-control" name="total" />
              </AvGroup>
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="sale-state" type="checkbox" className="form-check-input" name="state" />
                  State
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/sale" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  saleEntity: storeState.sale.entity,
  loading: storeState.sale.loading,
  updating: storeState.sale.updating,
  updateSuccess: storeState.sale.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SaleUpdate);
