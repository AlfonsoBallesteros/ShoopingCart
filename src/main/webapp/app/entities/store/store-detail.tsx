import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './store.reducer';
import { IStore } from 'app/shared/model/store.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStoreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StoreDetail = (props: IStoreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { storeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Store [<b>{storeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{storeEntity.date ? <TextFormat value={storeEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{storeEntity.quantity}</dd>
          <dt>
            <span id="move">Move</span>
          </dt>
          <dd>{storeEntity.move}</dd>
          <dt>Product</dt>
          <dd>{storeEntity.productId ? storeEntity.productId : ''}</dd>
        </dl>
        <Button tag={Link} to="/store" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/store/${storeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ store }: IRootState) => ({
  storeEntity: store.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StoreDetail);
